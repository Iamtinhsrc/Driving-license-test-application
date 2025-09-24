package com.example.driving_car_project.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driving_car_project.data.model.Question
import com.example.driving_car_project.data.source.Repository
import com.example.driving_car_project.data.source.remote.ResponseResult
import com.example.driving_car_project.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<QuestionUiState>(QuestionUiState.Idle)
    val uiState: StateFlow<QuestionUiState> = _uiState

    private val _selectedQuestion = MutableStateFlow<Question?>(null)
    val selectedQuestion: StateFlow<Question?> = _selectedQuestion


    fun loadLocalOrRemoteQuestions() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.value = QuestionUiState.Loading
            try {
                val local = repository.getLocalQuestions()
                if(local.isNotEmpty()){
                    _uiState.value = QuestionUiState.Success(local)
                } else {
                    when(val r = repository.fetchQuestions()) {
                        is ResponseResult.Success -> {
                            val cached = repository.getLocalQuestions()
                            _uiState.value = QuestionUiState.Success(cached)
                        }
                        is ResponseResult.Error -> {
                            _uiState.value = QuestionUiState.Error(r.message)
                        }
                        else -> {
                            _uiState.value = QuestionUiState.Loading
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = QuestionUiState.Error(e.message ?: "Lỗi load câu hỏi")
            }
        }
    }

    // Load theo Category
    fun loadByType(typeId: Int) {
        viewModelScope.launch(ioDispatcher) {
            _uiState.value = QuestionUiState.Loading
            try {
                val list = when {
                    typeId < 0 -> repository.getLocalQuestions()
                    typeId == 2001 -> repository.getCriticalQuestions()
                    else -> repository.getQuestionsByType(typeId)
                }
                    _uiState.value = QuestionUiState.Success(list)
            } catch (e: Exception) {
                _uiState.value = QuestionUiState.Error(e.message ?: "Lỗi load theo Category")
            }
        }
    }

    // Lay chi tiet 1 cau
    fun loadQuestionById(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            try {
                val q = repository.getQuestionById(id)
                _selectedQuestion.value = q
            } catch (e: Exception) {
                _selectedQuestion.value = null
            }
        }
    }

}

sealed class QuestionUiState {
    data object Idle: QuestionUiState()
    data object Loading: QuestionUiState()
    data class Success(val questions: List<Question>): QuestionUiState()
    data class Error(val message: String): QuestionUiState()
}
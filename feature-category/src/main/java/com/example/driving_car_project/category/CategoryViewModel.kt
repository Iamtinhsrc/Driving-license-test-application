package com.example.driving_car_project.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driving_car_project.model.QuestionType
import com.example.driving_car_project.remote.ResponseResult
import com.example.driving_car_project.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: com.example.driving_car_project.Repository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.value = CategoryUiState.Loading
            try {
                val local = repository.getLocalQuestionTypes()
                if(local.isNotEmpty()){
                    _uiState.value = CategoryUiState.Success(local)
                } else {
                    when (val r = repository.fetchQuestionTypes()) {
                        is ResponseResult.Success -> {
                            val types = repository.getLocalQuestionTypes()
                            _uiState.value = CategoryUiState.Success(types)
                        }
                        is ResponseResult.Error -> _uiState.value = CategoryUiState.Error(r.message)
                        is ResponseResult.Loading -> _uiState.value = CategoryUiState.Loading
                    }
                }
            } catch (e: Exception) {
                _uiState.value = CategoryUiState.Error(e.message ?: "Lỗi load danh mục")
            }
        }
    }

}

sealed class CategoryUiState {
    data object Loading: CategoryUiState()
    data class Success(val types: List<QuestionType>) : CategoryUiState()
    data class Error(val message: String) : CategoryUiState()
}
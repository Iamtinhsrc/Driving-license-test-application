package com.example.driving_car_project.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driving_car_project.data.model.HistoryWithExam
import com.example.driving_car_project.data.source.Repository
import com.example.driving_car_project.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamHistoryViewModel @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _histories = MutableStateFlow<List<HistoryWithExam>>(emptyList())
    val histories: StateFlow<List<HistoryWithExam>> = _histories

    init {
        viewModelScope.launch(ioDispatcher) {
            repository.getAllExamHistories()
                .collect { list ->
                    Log.d("ExamHistoryVM", "Histories loaded: ${list.size}")
                    _histories.value = list
                    list.forEach {
                        Log.d("ExamHistoryVM", "History: ${it.history.examId}, Exam: ${it.exam?.title}")
                    }

                }
        }
    }



}
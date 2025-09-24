package com.example.driving_car_project.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.driving_car_project.data.model.Exam
import com.example.driving_car_project.data.model.HistoryWithExam
import com.example.driving_car_project.data.source.Repository
import com.example.driving_car_project.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val exams = repository.getAllExams()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList<Exam>())

    private val _histories = MutableStateFlow<List<HistoryWithExam>>(emptyList())
    val histories: StateFlow<List<HistoryWithExam>> =_histories


}
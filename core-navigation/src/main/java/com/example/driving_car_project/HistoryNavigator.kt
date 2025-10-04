package com.example.driving_car_project

import androidx.fragment.app.Fragment

interface HistoryNavigator {
    fun navigateToResult(from: Fragment, examId: Int)
}
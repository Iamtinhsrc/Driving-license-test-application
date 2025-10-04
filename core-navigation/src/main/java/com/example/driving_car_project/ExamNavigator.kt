package com.example.driving_car_project

import androidx.fragment.app.Fragment

interface ExamNavigator {
    fun navigateToExamDetail(from: Fragment, examId: Int, isPreview: Boolean = false)
    fun navigateToExamResult(from: Fragment, examId: Int)
    fun navigateToHome(from: Fragment)
}
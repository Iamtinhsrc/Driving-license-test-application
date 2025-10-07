package com.example.driving_car_project.di

import androidx.fragment.app.Fragment
import com.example.driving_car_project.ExamNavigator

class FakeExamNavigator : ExamNavigator {
    override fun navigateToExamDetail(from: Fragment, examId: Int, isPreview: Boolean) {
        //
    }

    override fun navigateToExamResult(from: Fragment, examId: Int) {
        //
    }

    override fun navigateToHome(from: Fragment) {
        //
    }
}

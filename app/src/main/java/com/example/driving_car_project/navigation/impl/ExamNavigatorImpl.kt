package com.example.driving_car_project.navigation.impl

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.driving_car_project.ExamNavigator
import com.example.driving_car_project.NavGraphDirections
import com.example.driving_car_project.exam.ExamDetailFragmentDirections
import com.example.driving_car_project.exam.ExamFragmentDirections
import javax.inject.Inject

class ExamNavigatorImpl @Inject constructor() : ExamNavigator {
    override fun navigateToExamDetail(from: Fragment, examId: Int, isPreview: Boolean) {
        from.findNavController().navigate(
            ExamFragmentDirections.actionExamFragmentToExamDetail(examId, isPreview)
        )
    }

    override fun navigateToExamResult(from: Fragment, examId: Int) {
        from.findNavController().navigate(
            ExamDetailFragmentDirections.actionExamDetailToResult(examId)
        )
    }

    override fun navigateToHome(from: Fragment) {
        val action = NavGraphDirections.actionGlobalHome()
        from.findNavController().navigate(action)
    }

}
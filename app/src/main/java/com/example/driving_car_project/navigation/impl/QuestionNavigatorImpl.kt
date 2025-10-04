package com.example.driving_car_project.navigation.impl

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.driving_car_project.QuestionNavigator
import com.example.driving_car_project.question.QuestionFragmentDirections
import javax.inject.Inject

class QuestionNavigatorImpl @Inject constructor() : QuestionNavigator {
    override fun navigateToDetail(from: Fragment, questionId: Int, categoryId: Int) {
        from.findNavController().navigate(
            QuestionFragmentDirections.actionQuestionToDetail(questionId, categoryId)
        )
    }

}
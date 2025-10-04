package com.example.driving_car_project

import androidx.fragment.app.Fragment

interface QuestionNavigator {
    fun navigateToDetail(from: Fragment, questionId: Int, categoryId: Int)
}
package com.example.driving_car_project

import androidx.fragment.app.Fragment

interface CategoryNavigator {
    fun navigateToQuestions(from: Fragment, categoryId: Int)
}
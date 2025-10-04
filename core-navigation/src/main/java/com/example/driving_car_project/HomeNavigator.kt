package com.example.driving_car_project

import androidx.fragment.app.Fragment

interface HomeNavigator {
    fun navigateToQuestions(from: Fragment)
    fun navigateToCategory(from: Fragment)
    fun navigateToExam(from: Fragment)
    fun navigateToHistory(from: Fragment)
    fun navigateToGuide(from: Fragment)
}
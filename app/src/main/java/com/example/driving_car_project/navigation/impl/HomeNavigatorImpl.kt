package com.example.driving_car_project.navigation.impl

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.driving_car_project.HomeNavigator
import com.example.driving_car_project.home.HomeFragmentDirections
import javax.inject.Inject

class HomeNavigatorImpl @Inject constructor() : HomeNavigator {
    override fun navigateToQuestions(from: Fragment) {
        from.findNavController().navigate(HomeFragmentDirections.actionHomeToQuestions())
    }

    override fun navigateToCategory(from: Fragment) {
        from.findNavController().navigate(HomeFragmentDirections.actionHomeToCategory())
    }

    override fun navigateToExam(from: Fragment) {
        from.findNavController().navigate(HomeFragmentDirections.actionHomeToExam())
    }

    override fun navigateToHistory(from: Fragment) {
        from.findNavController().navigate(HomeFragmentDirections.actionHomeToHistory())
    }

    override fun navigateToGuide(from: Fragment) {
        from.findNavController().navigate(HomeFragmentDirections.actionHomeToGuide())
    }

}
package com.example.driving_car_project.navigation.impl

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.driving_car_project.CategoryNavigator
import com.example.driving_car_project.category.CategoryFragmentDirections
import javax.inject.Inject

class CategoryNavigatorImpl @Inject constructor() : CategoryNavigator {
    override fun navigateToQuestions(from: Fragment, categoryId: Int) {
        from.findNavController().navigate(
            CategoryFragmentDirections.actionCategoryToQuestions(categoryId)
        )
    }
}
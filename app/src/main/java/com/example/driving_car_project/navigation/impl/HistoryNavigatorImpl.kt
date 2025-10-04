package com.example.driving_car_project.navigation.impl

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.driving_car_project.HistoryNavigator
import com.example.driving_car_project.NavGraphDirections
import javax.inject.Inject

class HistoryNavigatorImpl @Inject constructor() : HistoryNavigator{
    override fun navigateToResult(from: Fragment, examId: Int) {
        val request = NavDeepLinkRequest.Builder
            .fromUri("app://exam_result/$examId".toUri())
            .build()

        from.findNavController().navigate(request)

    }

}
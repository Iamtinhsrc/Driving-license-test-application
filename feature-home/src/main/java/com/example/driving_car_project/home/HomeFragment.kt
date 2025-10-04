package com.example.driving_car_project.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.driving_car_project.HomeNavigator
import com.example.driving_car_project.feature.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var navigator: HomeNavigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnQuestions.setOnClickListener {
            // QuestionFragment default categoryId = -1
            navigator.navigateToQuestions(this)
        }

        binding.btnCategory.setOnClickListener {
            navigator.navigateToCategory(this)
        }

        binding.btnExam.setOnClickListener {
            navigator.navigateToExam(this)
        }

        binding.btnHistory.setOnClickListener {
            navigator.navigateToHistory(this)
        }

        binding.btnGuide.setOnClickListener {
            navigator.navigateToGuide(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
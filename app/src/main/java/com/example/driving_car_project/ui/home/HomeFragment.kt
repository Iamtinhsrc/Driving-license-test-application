package com.example.driving_car_project.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.driving_car_project.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
            val action = HomeFragmentDirections.actionHomeToQuestions()
            findNavController().navigate(action)
        }

        binding.btnCategory.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToCategory()
            findNavController().navigate(action)
        }

        binding.btnExam.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToExam()
            findNavController().navigate(action)
        }

        binding.btnHistory.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToHistory()
            findNavController().navigate(action)
        }

        binding.btnGuide.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToGuide()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
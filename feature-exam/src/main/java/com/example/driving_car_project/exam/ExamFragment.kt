package com.example.driving_car_project.exam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.driving_car_project.ExamNavigator
import com.example.driving_car_project.feature.exam.databinding.FragmentExamBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExamFragment : Fragment() {

    private var _binding: FragmentExamBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var navigator: ExamNavigator

    private val viewModel: ExamViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.toolbarExam.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnExam10.setOnClickListener {
            viewModel.createAndStartExam(10, "Đề 10 câu")
        }
        binding.btnExam20.setOnClickListener {
            viewModel.createAndStartExam(20, "Đề 20 câu")
        }
        binding.btnExam30.setOnClickListener {
            viewModel.createAndStartExam(30, "Đề 30 câu")
        }

        lifecycleScope.launch {
            viewModel.currentExam.collectLatest { exam ->
                exam?.let {
//                    val action = ExamFragmentDirections.actionExamFragmentToExamDetail(it.id)
//                    findNavController().navigate(action)
                    navigator.navigateToExamDetail(this@ExamFragment, exam.id)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
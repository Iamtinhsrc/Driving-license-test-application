package com.example.driving_car_project.ui.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController


import com.example.driving_car_project.databinding.FragmentQuestionBinding
import com.example.driving_car_project.ui.viewmodel.QuestionUiState
import com.example.driving_car_project.ui.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuestionViewModel by viewModels()
    private lateinit var adapter: QuestionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Lay categoryId tu args (mac dinh = -1)
        val args = QuestionFragmentArgs.fromBundle(requireArguments())
        val categoryId = args.categoryId

        adapter = QuestionAdapter(emptyList()) { question ->
            val action = QuestionFragmentDirections.actionQuestionToDetail(
                question.id,
                categoryId)
            findNavController().navigate(action)
        }

        binding.toolbarQuestion.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        //binding.rvQuestions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvQuestions.adapter = adapter

        if (categoryId < 0) {
            viewModel.loadLocalOrRemoteQuestions()
        } else {
            viewModel.loadByType(categoryId)
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when(state) {
                    is QuestionUiState.Success -> {
                        adapter.updateData(state.questions)
                    }
                    is QuestionUiState.Error -> {
                        //
                    }

                    is QuestionUiState.Idle -> {
                        //
                    }
                    is QuestionUiState.Loading -> {
                        //
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
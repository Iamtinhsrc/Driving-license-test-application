package com.example.driving_car_project.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.driving_car_project.question.databinding.FragmentQuestionDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionDetailFragment : Fragment() {

    private var _binding: FragmentQuestionDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuestionViewModel by viewModels()
    private lateinit var pagerAdapter: QuestionPageAdapter

    private var currentQuestionId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = QuestionDetailFragmentArgs.fromBundle(requireArguments())
        currentQuestionId = args.questionId
        val categoryId = args.categoryId

        pagerAdapter = QuestionPageAdapter(emptyList())
        binding.viewPagerQuestions.adapter = pagerAdapter

//        binding.toolbarQuestionDetail.setNavigationOnClickListener {
//            findNavController().navigateUp()
//        }

        // Load câu hỏi
        if (categoryId == 60) {
            viewModel.loadByType(60)
        } else if (categoryId < 0) {
            viewModel.loadLocalOrRemoteQuestions()
        } else {
            viewModel.loadByType(categoryId)
        }

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                if (state is QuestionUiState.Success) {
                    pagerAdapter.setData(state.questions)
                    // tìm vị trí của questionId ban đầu
                    val idx = state.questions.indexOfFirst { it.id == currentQuestionId }
                    if (idx >= 0) {
                        binding.viewPagerQuestions.setCurrentItem(idx, false)
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

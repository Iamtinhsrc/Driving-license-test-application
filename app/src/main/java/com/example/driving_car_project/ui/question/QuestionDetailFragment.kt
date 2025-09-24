package com.example.driving_car_project.ui.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.driving_car_project.R
import com.example.driving_car_project.data.model.Question
import com.example.driving_car_project.databinding.FragmentQuestionDetailBinding
import com.example.driving_car_project.ui.viewmodel.QuestionUiState
import com.example.driving_car_project.ui.viewmodel.QuestionViewModel
import com.example.driving_car_project.util.toAnswerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionDetailFragment : Fragment() {

    private var _binding: FragmentQuestionDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuestionViewModel by viewModels()
    private lateinit var adapter: QuestionDetailAdapter

    //current question list(for prev/next)
    private var currentList: List<Question> = emptyList()
    private var currentIndex: Int = 0
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

        if (categoryId == 60) {
            viewModel.loadByType(60)
        } else if (categoryId < 0) {
            viewModel.loadLocalOrRemoteQuestions()
        } else {
            viewModel.loadByType(categoryId)
        }


        adapter = QuestionDetailAdapter(emptyList()) { option ->
            //
        }
        binding.rvAnswers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAnswers.adapter = adapter

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when(state) {
                    is QuestionUiState.Success -> {
                        currentList = state.questions
                        val idx = currentList.indexOfFirst { it.id == currentQuestionId }
                        if (idx >= 0){
                            currentIndex = idx
                            displayQuestion(currentList[currentIndex])
                        } else if (currentList.isNotEmpty()) {
                            currentIndex = 0
                            currentQuestionId = currentList[0].id
                            displayQuestion(currentList[0])
                        }
                    }

                    is QuestionUiState.Error -> {
                        //
                    }
                    QuestionUiState.Idle -> {
                        //
                    }
                    QuestionUiState.Loading -> {
                        //
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.selectedQuestion.collectLatest { q ->
                q?.let {
                    displayQuestion(it)
                    val idx = currentList.indexOfFirst { q2 -> q2.id == it.id }
                    if (idx >= 0) currentIndex = idx
                }
            }
        }

        viewModel.loadQuestionById(currentQuestionId)

        // QuestionDetailFragment.kt (đổi phần click handler)
        binding.btnCheckAnswer.setOnClickListener {
            val selected = adapter.getSelectedOption()
            if (selected == null) {
                Toast.makeText(requireContext(), "Vui lòng chọn 1 đáp án", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // show UI tick/x trong adapter
            adapter.checkAnswer()

            // Lấy đáp án đúng từ chính adapter (đảm bảo 100% khớp với UI)
            val correctOption = adapter.getCorrectOption()

            if (selected.isCorrect) {
                // Chọn đúng → hiện thông báo + suggest của đáp án đúng
                binding.tvAnswerResult.text = "Bạn chọn đúng!"
            } else {
                // Chọn sai → hiện "Bạn chọn sai. Đáp án đúng: X"
                binding.tvAnswerResult.text = "Bạn chọn sai. Đáp án đúng: ${correctOption?.label ?: "?"}"
            }

            // luôn hiển thị suggest của đáp án đúng
            binding.tvExplanation.text = correctOption?.suggest ?: "Không có giải thích"
            binding.llResult.visibility = View.VISIBLE
        }




        binding.btnPrevQuestion.setOnClickListener {
            if (currentList.isNotEmpty() && currentIndex > 0) {
                currentIndex -= 1
                currentQuestionId = currentList[currentIndex].id
                displayQuestion(currentList[currentIndex])
            }
        }

        binding.btnNextQuestion.setOnClickListener {
            if (currentList.isNotEmpty() && currentIndex < currentList.size - 1) {
                currentIndex += 1
                currentQuestionId = currentList[currentIndex].id
                displayQuestion(currentList[currentIndex])
            }
        }

    }

    private fun displayQuestion(q: Question) {
        binding.tvQuestionIndex.text = "Câu ${currentIndex + 1}/${currentList.size.coerceAtLeast(1)}"
        binding.tvQuestionDetail.text = q.question

        val imageUrl = q.image.img1 ?: q.image.img2 ?: q.image.img3 ?: q.image.img4
        if(!imageUrl.isNullOrEmpty()) {
            binding.imgQuestion.visibility = View.VISIBLE
            Glide.with(requireContext())
                .load(imageUrl)
                .error(R.drawable.ic_question)
                .into(binding.imgQuestion)
        } else {
            binding.imgQuestion.visibility = View.GONE
        }

        // set options
        val options = q.toAnswerOptions()
        adapter.setData(options)
        binding.llResult.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.driving_car_project.exam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.driving_car_project.ExamNavigator
import com.example.driving_car_project.feature.exam.R
import com.example.driving_car_project.model.AnswerOption
import com.example.driving_car_project.model.Question
import com.example.driving_car_project.feature.exam.databinding.FragmentExamDetailBinding
import com.example.driving_car_project.util.toAnswerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ExamDetailFragment : Fragment() {

    private var _binding: FragmentExamDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var navigator: ExamNavigator


    private val viewModel: ExamViewModel by viewModels()
    private lateinit var adapter: ExamDetailAdapter

    private var currentQuestionIndex = 0
    private var currentQuestions: List<Question> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = ExamDetailFragmentArgs.fromBundle(requireArguments())
        val examId = args.examId
        val isPreview = args.isPreview

        viewModel.loadExam(examId, isPreview)

        adapter = ExamDetailAdapter(emptyList()) { option ->
            if(!isPreview) {
                val q = currentQuestions.getOrNull(currentQuestionIndex) ?: return@ExamDetailAdapter
                viewModel.chooseAnswer(q.id, option.label)
            }
        }
        binding.rvExamAnswers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvExamAnswers.adapter = adapter

        if (isPreview) {
            adapter.isReviewMode = true
            adapter.showResult()
            binding.btnSubmitExam.visibility = View.GONE
            binding.btnGoHome.visibility = View.VISIBLE
        } else {
            binding.btnSubmitExam.visibility = View.VISIBLE
            binding.btnGoHome.visibility = View.GONE
        }


        lifecycleScope.launch {
            viewModel.questions.collectLatest { list ->
                currentQuestions = list
                if(list.isNotEmpty()){
                    currentQuestionIndex = viewModel.currentIndex.value
                    showQuestionAt(currentQuestionIndex)
                } else {
                    binding.tvExamQuestion.text = getString(R.string.default_exam_question)
                    adapter.updateOption(emptyList())
                }
            }
        }

        lifecycleScope.launch {
            viewModel.currentIndex.collectLatest { idx ->
                currentQuestionIndex = idx
                showQuestionAt(idx)
            }
        }

        lifecycleScope.launch {
            viewModel.selectedAnswers.collectLatest { map ->
                val q = currentQuestions.getOrNull(currentQuestionIndex)
                if(q != null) {
                    val select = map[q.id]
                    adapter.setSelected(select)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.examTimeLeft.collectLatest { seconds ->
                val mm = seconds / 60
                val ss = seconds % 60
                binding.tvTimer.text = String.format("%02d:%02d", mm, ss)
            }
        }


        lifecycleScope.launch {
            viewModel.currentIndex
                .combine(viewModel.questionTimeLeft) { index, map ->
                    val qId = viewModel.questions.value.getOrNull(index)?.id
                    map[qId] ?: 0
                }
                .collectLatest { seconds ->
                    val mm = seconds / 60
                    val ss = seconds % 60
                    binding.tvQuestionTimer.text = String.format("%02d:%02d", mm, ss)
                }
        }


        lifecycleScope.launch {
            viewModel.resultEvents.collectLatest { result ->
//                val action = ExamDetailFragmentDirections.actionExamDetailToResult(result.examId)
//                findNavController().navigate(action)
                navigator.navigateToExamResult(this@ExamDetailFragment, result.examId)
            }
        }
        lifecycleScope.launch {
            viewModel.showSubmitConfirm.collectLatest {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.dialog_confirm))
                    .setMessage(getString(R.string.dialog_message))
                    .setPositiveButton(getString(R.string.dialog_submit)) { _, _ -> viewModel.submitExam() }
                    .setNegativeButton(getString(R.string.dialog_cancel), null)
                    .show()
            }
        }


        binding.btnNext.setOnClickListener {
            viewModel.next()
        }
        binding.btnPrev.setOnClickListener {
            viewModel.prev()
        }
        binding.btnSubmitExam.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.dialog_confirm))
                .setMessage(getString(R.string.dialog_message_submit))
                .setPositiveButton(getString(R.string.dialog_submit)) { _, _ ->
                    try {
                        viewModel.submitExam()
                    } catch (e: Exception) {
                        Toast.makeText(
                            requireContext(),
                            "Lỗi khi nộp bài: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton(getString(R.string.dialog_cancel), null)
                .show()
        }
        binding.btnGoHome.setOnClickListener {
            navigator.navigateToHome(this)
        }


    }

    private fun showQuestionAt(index: Int) {
        val q = currentQuestions.getOrNull(index)
        if(q == null) {
            binding.tvExamQuestion.text = ""
            adapter.updateOption(emptyList())
            binding.imgExamQuestion.visibility = View.GONE
            binding.tvQuestionTimer.text = getString(R.string.default_question_time)
            return
        }

        binding.tvQuestionIndex.text = "Câu ${index + 1}/${currentQuestions.size}"
        binding.tvExamQuestion.text = q.question
        val imageUrl = q.image.img1 ?: q.image.img2 ?: q.image.img3 ?: q.image.img4
        if(!imageUrl.isNullOrBlank()) {
            binding.imgExamQuestion.visibility = View.VISIBLE
            Glide.with(requireContext())
                .load(imageUrl)
                .error(R.drawable.ic_question)
                .into(binding.imgExamQuestion)
        } else {
            binding.imgExamQuestion.visibility = View.GONE
        }

        val opts: List<AnswerOption> = q.toAnswerOptions()
        adapter.updateOption(opts)

        val selected = viewModel.selectedAnswers.value[q.id]
        if(selected != null) {
            adapter.setSelected(selected)
        } else {
            adapter.setSelected(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
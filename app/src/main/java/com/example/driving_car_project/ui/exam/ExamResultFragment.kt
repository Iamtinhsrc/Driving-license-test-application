package com.example.driving_car_project.ui.exam

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.driving_car_project.R
import com.example.driving_car_project.databinding.FragmentExamResultBinding
import com.example.driving_car_project.ui.viewmodel.ExamHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExamResultFragment : Fragment() {

    private var _binding: FragmentExamResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExamHistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = ExamResultFragmentArgs.fromBundle(requireArguments())
        val examId = args.examId

        lifecycleScope.launch {
            viewModel.histories.collectLatest { list ->
                val matched = list.firstOrNull { it.history.examId == examId}
                if (matched != null) {
                    binding.tvResultSummary.text = "Kết quả: ${matched.history.score}/${matched.history.total} câu đúng"
                    if (matched.history.passed) {
                        binding.tvResultStatus.text = "Đậu"
                        binding.tvResultStatus.setBackgroundResource(R.drawable.bg_badge_pass)
                    } else {
                        binding.tvResultStatus.text = "Trượt"
                        binding.tvResultStatus.setBackgroundResource(R.drawable.bg_badge_fail)
                    }

                    val duration = matched.history.durationSeconds
                    val mm = duration / 60
                    val ss = duration % 60
                    binding.tvResultTime.text = "Thời gian làm: %02d:%02d".format(mm, ss)

                } else {
                    binding.tvResultSummary.text = "Kết quả: --"
                    binding.tvResultStatus.text = ""
                    binding.tvResultTime.text = ""
                }
            }
        }

        binding.btnReviewExam.setOnClickListener {
            // Open ExamDetail in Preview mode
            val action = ExamResultFragmentDirections.actionResultToExamDetail(examId, true)
            findNavController().navigate(action)
        }

        binding.btnHome.setOnClickListener {
            val action = ExamResultFragmentDirections.actionResultToHome()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
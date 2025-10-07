package com.example.driving_car_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.driving_car_project.feature.history.databinding.FragmentExamHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExamHistoryFragment : Fragment() {

    private var _binding: FragmentExamHistoryBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var navigator: HistoryNavigator

    private val viewModel: ExamHistoryViewModel by viewModels()
    private lateinit var adapter: ExamHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ExamHistoryAdapter(emptyList()) { historyWithExam ->
            //val action = ExamHistoryFragmentDirections.actionHistoryToResult(historyWithExam.history.id)
            //findNavController().navigate(action)

            navigator.navigateToResult(this, historyWithExam.history.id)

        }

//        binding.toolbarHistory.setNavigationOnClickListener {
//            findNavController().navigateUp()
//        }

        binding.rvHistory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvHistory.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.histories.collectLatest { list ->
                    if (list.isEmpty()) {
                        binding.llEmpty.visibility = View.VISIBLE
                        binding.rvHistory.visibility = View.GONE
                    } else {
                        binding.llEmpty.visibility = View.GONE
                        binding.rvHistory.visibility = View.VISIBLE
                        adapter.submitList(list)
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
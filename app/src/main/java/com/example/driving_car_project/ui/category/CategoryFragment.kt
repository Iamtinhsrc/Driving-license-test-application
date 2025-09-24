package com.example.driving_car_project.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.driving_car_project.databinding.FragmentCategoryBinding
import com.example.driving_car_project.ui.viewmodel.CategoryUiState
import com.example.driving_car_project.ui.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CategoryAdapter(emptyList()) { category ->
            val action = CategoryFragmentDirections.actionCategoryToQuestions(category.id)
            findNavController().navigate(action)
        }

        binding.toolbarCategory.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.rvCategories.adapter = adapter

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                when(state) {
                    is CategoryUiState.Success -> {
                        adapter.updateData(state.types)
                    }
                    is CategoryUiState.Error -> {
                        //
                    }
                    is CategoryUiState.Loading -> {

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
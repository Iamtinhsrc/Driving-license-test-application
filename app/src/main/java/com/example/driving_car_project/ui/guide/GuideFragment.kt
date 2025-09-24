package com.example.driving_car_project.ui.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import com.example.driving_car_project.R
import com.example.driving_car_project.databinding.FragmentGuideBinding
import java.io.BufferedReader
import java.io.InputStreamReader


class GuideFragment : Fragment() {

    private var _binding: FragmentGuideBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         _binding = FragmentGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarGuide.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val inputStream = resources.openRawResource(R.raw.guide)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val content = reader.use { it.readText() }

        val styledText = HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvGuide.text = styledText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
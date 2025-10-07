package com.example.driving_car_project.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.example.driving_car_project.feature.guide.databinding.FragmentGuidePageBinding

class GuidePageFragment : Fragment() {

    companion object {
        private const val ARG_CONTENT = "content"

        fun newInstance(content: String) = GuidePageFragment().apply {
            arguments = Bundle().apply { putString(ARG_CONTENT, content) }
        }
    }

    private var _binding: FragmentGuidePageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuidePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val content = arguments?.getString(ARG_CONTENT)
        val styledText = HtmlCompat.fromHtml(content ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvGuidePage.text = styledText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

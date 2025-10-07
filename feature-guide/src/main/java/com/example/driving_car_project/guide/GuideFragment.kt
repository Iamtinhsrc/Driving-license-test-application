package com.example.driving_car_project.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.driving_car_project.feature.guide.R
import com.example.driving_car_project.feature.guide.databinding.FragmentGuideBinding
import com.google.android.material.tabs.TabLayoutMediator


class GuideFragment : Fragment() {

    private var _binding: FragmentGuideBinding? = null
    private val binding get() = _binding!!

    private val titles = listOf(
        "I. CÁC KHÁI NIỆM CƠ BẢN",
        "II. QUY TẮC GIAO THÔNG",
        "III. TỐC ĐỘ & KHOẢNG CÁCH AN TOÀN",
        "IV. HOẠT ĐỘNG VẬN TẢI",
        "V. ĐỘNG CƠ & HỆ THỐNG Ô TÔ",
        "VI. KỸ THUẬT LÁI XE",
        "VII. BIỂN BÁO & SA HÌNH"
    )

    private val pages = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputStream = resources.openRawResource(R.raw.guide)
        val content = inputStream.bufferedReader().use { it.readText() }

        // tách nội dung theo các mục chính
        val regex = Regex("<h3>(.*?)</h3>")
        val matches = regex.findAll(content).toList()
        for (i in matches.indices) {
            val start = matches[i].range.last + 1
            val end = if (i < matches.size - 1) matches[i + 1].range.first else content.length
            pages.add(content.substring(start, end))
        }

        // ViewPager2 + Adapter
        val adapter = GuidePagerAdapter(this, pages)
        binding.viewPager.adapter = adapter

        // TabLayout với tiêu đề của từng trang
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.driving_car_project.guide

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class GuidePagerAdapter(
    fragment: Fragment,
    private val pages: List<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = pages.size

    override fun createFragment(position: Int): Fragment {
        return GuidePageFragment.newInstance(pages[position])
    }
}

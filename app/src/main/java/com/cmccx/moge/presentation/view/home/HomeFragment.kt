package com.cmccx.moge.presentation.view.home

import androidx.viewpager2.widget.ViewPager2
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private val cateList = arrayListOf<String>()

    init {
        // dummy data
        cateList.add("1번")
        cateList.add("2번")
        cateList.add("3번")
    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }

    private fun setAdapter() {
        // Top AD ViewPager
        binding.homeQuizVp.adapter = FavoriteCategoryAdapter(cateList)
        binding.homeQuizVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.homeQuizCi.setViewPager(binding.homeQuizVp)
    }
}
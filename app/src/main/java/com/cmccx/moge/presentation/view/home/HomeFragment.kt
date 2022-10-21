package com.cmccx.moge.presentation.view.home

import android.os.Bundle
import android.system.Os.bind
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private val cateList = arrayListOf<String>()

    init {
        // dummy data
        cateList.add("1번 카테")
        cateList.add("2번 카테")
        cateList.add("3번 카테 ")
    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }

    private fun setAdapter() {
        // 피드 프로필
        binding.homeFeedProfileRcv.adapter = FeedProfileAdapter()
        binding.homeFeedProfileRcv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 퀴즈 뷰페이저
        binding.homeQuizVp.adapter = FavoriteCategoryAdapter(this.requireContext(), cateList)
        binding.homeQuizVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.homeQuizCi.setViewPager(binding.homeQuizVp)
    }
}
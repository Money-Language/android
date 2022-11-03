package com.cmccx.moge.presentation.view.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentHomeBinding
import com.cmccx.moge.presentation.view.MainOwner
import com.cmccx.moge.presentation.viewmodel.HomeViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private lateinit var owner: MainOwner
    private val viewModel: HomeViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        owner.setActionBarVisible(false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.homeTodayQuizBtnCl.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
        }
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
        binding.homeQuizVp.adapter = FavoriteCategoryAdapter(this.requireContext(), viewModel)
        binding.homeQuizVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.homeQuizCi.setViewPager(binding.homeQuizVp)
    }

}
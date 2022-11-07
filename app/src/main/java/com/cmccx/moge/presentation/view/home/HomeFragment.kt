package com.cmccx.moge.presentation.view.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.base.getJwt
import com.cmccx.moge.base.getUserIdx
import com.cmccx.moge.data.remote.api.CategoryView
import com.cmccx.moge.data.remote.api.HomeInterestedBoardView
import com.cmccx.moge.databinding.FragmentHomeBinding
import com.cmccx.moge.presentation.view.MainOwner
import com.cmccx.moge.presentation.view.quiz.QuizCommentAdapter
import com.cmccx.moge.presentation.viewmodel.HomeViewModel
import com.cmccx.moge.presentation.viewmodel.PointViewModel


class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), CategoryView, HomeInterestedBoardView {

    private lateinit var owner: MainOwner
    private val viewModel: HomeViewModel by activityViewModels()
    private val pointViewModel: PointViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        owner.setActionBarVisible(false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.point = pointViewModel

        pointViewModel.getPoint(getJwt(this.requireContext()), getUserIdx(this.requireContext()))

        binding.homeTodayQuizBtnCl.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCategory(this, getJwt(this.requireContext()), getUserIdx(this.requireContext()))
    }

    private fun setAdapter() {
        // 피드 프로필
        binding.homeFeedProfileRcv.adapter = FeedProfileAdapter()
        binding.homeFeedProfileRcv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 퀴즈 뷰페이저
        binding.homeQuizVp.adapter = FavoriteCategoryAdapter(this.requireContext(), viewModel.quizCate.value!!, viewModel)
        binding.homeQuizVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.homeQuizCi.setViewPager(binding.homeQuizVp)
    }

    // 카테고리 호출 콜백
    override fun onGetCategoryResultSuccess() {
        viewModel.getBoard(this, 3)
    }

    override fun onGetCategoryResultFailure(message: String) {
        TODO("Not yet implemented")
    }

    // 보드 호출 콜백
    override fun onGetBoardSuccess() {
        setAdapter()
    }

    override fun onGetBoardFailure(message: String) {
        TODO("Not yet implemented")
    }

}
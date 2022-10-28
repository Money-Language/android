package com.cmccx.moge.presentation.view.quiz

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentQuizBinding
import com.cmccx.moge.presentation.view.MainOwner
import com.cmccx.moge.presentation.view.home.FavoriteCategoryAdapter
import com.cmccx.moge.presentation.viewmodel.QuizViewModel

class QuizFragment: BaseFragment<FragmentQuizBinding>(FragmentQuizBinding::bind, R.layout.fragment_quiz) {

    private lateinit var owner: MainOwner

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainOwner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        owner.setBottomNavVisible(false)
        owner.setFloatingBtnVisible(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        owner.setActionBarVisible(true)
    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        owner.setBottomNavVisible(true)
        owner.setFloatingBtnVisible(true)
    }

    private fun setAdapter() {
        // 퀴즈 뷰페이저
        binding.quizBoardVp.adapter = QuizAdapter(this.requireContext())
        binding.quizBoardVp.orientation = ViewPager2.ORIENTATION_VERTICAL
    }
}
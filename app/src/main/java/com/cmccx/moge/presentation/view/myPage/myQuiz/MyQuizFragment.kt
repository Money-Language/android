package com.cmccx.moge.presentation.view.myPage.myQuiz

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentMyQuizBinding

class MyQuizFragment : BaseFragment<FragmentMyQuizBinding>(FragmentMyQuizBinding::bind, R.layout.fragment_my_quiz) {

    private val cateList = arrayListOf<String>()
    private val quizBoardList = arrayListOf<String>()

    init {
        cateList.add("dummy1")
        cateList.add("dummy2")
        cateList.add("dummy3")
        cateList.add("dummy4")
        cateList.add("dummy5")
        cateList.add("dummy6")
        cateList.add("dummy7")

        quizBoardList.add("dummy1")
        quizBoardList.add("dummy2")
        quizBoardList.add("dummy3")
        quizBoardList.add("dummy4")
        quizBoardList.add("dummy5")
        quizBoardList.add("dummy6")
        quizBoardList.add("dummy7")
        quizBoardList.add("dummy8")
        quizBoardList.add("dummy9")
        quizBoardList.add("dummy10")
    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }

    private fun setAdapter() {
        // 카테 헤더
        binding.itemMyquizQuizCateHeaderRv.adapter = QuizCateAdapter(this.requireContext(), cateList)
        binding.itemMyquizQuizCateHeaderRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 퀴즈 보드
        binding.itemMyquizQuizBoardsRv.adapter = QuizBoardAdapter(quizBoardList)
        binding.itemMyquizQuizBoardsRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }

}
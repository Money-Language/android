package com.cmccx.moge.presentation.view.search

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.data.remote.api.TopLikeView
import com.cmccx.moge.data.remote.api.TopViewView
import com.cmccx.moge.databinding.FragmentSearchBinding
import com.cmccx.moge.presentation.view.myPage.myQuiz.QuizBoardAdapter
import com.cmccx.moge.presentation.view.myPage.myQuiz.QuizCateAdapter

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search), TopViewView, TopLikeView{
    private val viewTopTenList = arrayListOf<String>()
    private val likeTopTenList = arrayListOf<String>()
    private val cateList = arrayListOf<String>()
    private val quizBoardList = arrayListOf<String>()

    init {
        viewTopTenList.add("1")
        viewTopTenList.add("2")
        viewTopTenList.add("3")
        viewTopTenList.add("4")
        viewTopTenList.add("5")
        viewTopTenList.add("6")
        viewTopTenList.add("7")
        viewTopTenList.add("8")
        viewTopTenList.add("9")
        viewTopTenList.add("10")

        likeTopTenList.add("1")
        likeTopTenList.add("2")
        likeTopTenList.add("3")
        likeTopTenList.add("4")
        likeTopTenList.add("5")
        likeTopTenList.add("6")
        likeTopTenList.add("7")
        likeTopTenList.add("8")
        likeTopTenList.add("9")
        likeTopTenList.add("10")

        cateList.add("dummy1")
        cateList.add("dummy2")
        cateList.add("dummy3")
        cateList.add("dummy4")
        cateList.add("dummy5")
        cateList.add("dummy6")
        cateList.add("dummy7")

        quizBoardList.add("dummy1")
        quizBoardList.add("dummy1")
        quizBoardList.add("dummy1")
        quizBoardList.add("dummy1")
        quizBoardList.add("dummy1")
        quizBoardList.add("dummy1")
        quizBoardList.add("dummy1")
        quizBoardList.add("dummy1")
        quizBoardList.add("dummy1")
        quizBoardList.add("dummy1")
    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }

    private fun setAdapter() {
        // 조회순 top 10
        binding.searchViewToptenRv.adapter = ViewTopTenAdapter(viewTopTenList)
        binding.searchViewToptenRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 좋아요 top 10
        binding.searchLikeToptenRv.adapter = LikeTopTenAdapter(likeTopTenList)
        binding.searchLikeToptenRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 카테
        binding.searchQuizCateHeaderRv.adapter = QuizCateAdapter(requireContext(), cateList)
        binding.searchQuizCateHeaderRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 퀴즈 보드
        binding.searchQuizBoardsRv.adapter = QuizBoardAdapter(quizBoardList)
        binding.searchQuizBoardsRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

    }

    override fun onGetTopViewResultSuccess() {
        TODO("Not yet implemented")
    }

    override fun onGetTopViewResultFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetTopLikeResultSuccess() {
        TODO("Not yet implemented")
    }

    override fun onGetTopLikeResultFailure(message: String) {
        TODO("Not yet implemented")
    }
}
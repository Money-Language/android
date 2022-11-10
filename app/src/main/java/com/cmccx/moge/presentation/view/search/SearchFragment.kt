package com.cmccx.moge.presentation.view.search

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.data.remote.api.TopLikeService
import com.cmccx.moge.data.remote.api.TopLikeView
import com.cmccx.moge.data.remote.api.TopViewService
import com.cmccx.moge.data.remote.api.TopViewView
import com.cmccx.moge.data.remote.model.TopTen
import com.cmccx.moge.databinding.FragmentSearchBinding
import com.cmccx.moge.presentation.view.myPage.myQuiz.QuizBoardAdapter
import com.cmccx.moge.presentation.view.myPage.myQuiz.QuizCateAdapter

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search), TopViewView, TopLikeView{
    private val cateList = arrayListOf<String>()
    private val quizBoardList = arrayListOf<String>()

    private lateinit var viewTopTenAdapter: ViewTopTenAdapter
    private lateinit var likeTopTenAdapter: LikeTopTenAdapter

    init {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }

    private fun initRecyclerView() {
        // 조회순 top 10
        getTopView()
        viewTopTenAdapter = ViewTopTenAdapter(requireContext())
        binding.searchViewToptenRv.adapter = viewTopTenAdapter
        binding.searchViewToptenRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 좋아요 top 10
        getTopLike()
        likeTopTenAdapter = LikeTopTenAdapter(requireContext())
        binding.searchLikeToptenRv.adapter = likeTopTenAdapter
        binding.searchLikeToptenRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun setAdapter() {
        // 카테
        binding.searchQuizCateHeaderRv.adapter = QuizCateAdapter(requireContext(), cateList)
        binding.searchQuizCateHeaderRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 퀴즈 보드
        binding.searchQuizBoardsRv.adapter = QuizBoardAdapter(quizBoardList)
        binding.searchQuizBoardsRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

    }

    private fun getTopView() {
        val topViewService = TopViewService(this)
        topViewService.getTopViewResult()
    }

    override fun onGetTopViewResultSuccess(result: ArrayList<TopTen>) {
        viewTopTenAdapter.addTopTen(result)
    }

    override fun onGetTopViewResultFailure(message: String) {
        Log.d(TAG, "조회순 top 10 실패 - $message")
    }

    private fun getTopLike() {
        val topLikeService = TopLikeService(this)
        topLikeService.getTopLikeResult()
    }

    override fun onGetTopLikeResultSuccess(result: ArrayList<TopTen>) {
        likeTopTenAdapter.addTopTen(result)
    }

    override fun onGetTopLikeResultFailure(message: String) {
        Log.d(TAG, "좋아요 top 10 실패 - $message")
    }
}
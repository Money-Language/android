package com.cmccx.moge.presentation.view.myPage.myQuiz

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.base.getJwt
import com.cmccx.moge.base.getUserIdx
import com.cmccx.moge.data.remote.api.MyQuizAllService
import com.cmccx.moge.data.remote.api.MyQuizAllView
import com.cmccx.moge.data.remote.api.MyQuizCateService
import com.cmccx.moge.data.remote.api.MyQuizCateView
import com.cmccx.moge.data.remote.model.MyQuiz
import com.cmccx.moge.databinding.FragmentMyQuizBinding
import com.cmccx.moge.presentation.view.MainOwner

class MyQuizFragment : BaseFragment<FragmentMyQuizBinding>(FragmentMyQuizBinding::bind, R.layout.fragment_my_quiz), MyQuizAllView, MyQuizCateView {
    private lateinit var owner: MainOwner
    private lateinit var categoryAdapter: MyQuizCateAdapter
    private lateinit var myQuizAdapter: MyQuizBoardAdapter
    private lateinit var myQuizAllAdapter: MyQuizAllAdapter

    private var cateList = mutableListOf<String>()
    private var quizAll = ArrayList<MyQuiz>()

    private var jwt: String = ""
    private var userIdx: Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jwt = getJwt(requireContext())
        userIdx = getUserIdx(requireContext())

        initView()
    }

    private fun initView() {
        // 내 퀴즈 전체 조회 -> 여기서 중복 없이 카테고리 이름만 따로 저장해야 함
        getMyQuizAll()

        // 카테고리 이름 - '모두' 추가
        cateList.add("모두")
    }

    private fun getMyQuizAll() {
        val myQuizAllService = MyQuizAllService(this)
        myQuizAllService.getMyQuizAll(jwt, userIdx)
    }

    override fun onGetMyQuizAllResultSuccess(result: ArrayList<MyQuiz>?) {
        if (result != null) {
            for(i in 0 until result.size) {
                // 중복 없이 카테고리 이름 저장
                getCateList(result[i].categoryName)
            }
            binding.itemMyquizQuizBoardsRv.visibility = View.VISIBLE
            binding.itemMyquizQuizCateHeaderRv.visibility = View.VISIBLE
            binding.itemMyFavNothingCl.visibility = View.GONE

            quizAll = result
        }

        // 카테고리 어댑터 설정
        setCateList()
    }

    override fun onGetMyQuizAllResultFailure(code: Int, message: String) {
        if(code == 2022) { // 유저가 올린 퀴즈가 없을 때
            binding.itemMyquizQuizBoardsRv.visibility = View.GONE
            binding.itemMyquizQuizCateHeaderRv.visibility = View.GONE
            binding.itemMyFavNothingCl.visibility = View.VISIBLE
        }
    }

    private fun getCateList(category: String) {
        cateList.add(category)
        cateList.toSet().toList() // 중복 제거
    }

    private fun setCateList() {
        // 카테 헤더
        categoryAdapter = MyQuizCateAdapter(cateList)
        binding.itemMyquizQuizCateHeaderRv.adapter = categoryAdapter
        binding.itemMyquizQuizCateHeaderRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 카테고리 선택 시 클릭 인터페이스 설정
        clickCategory()
    }

    private fun clickCategory() {
        categoryAdapter.setCategoryClickListener(object: MyQuizCateAdapter.CategoryClickListener{
            override fun onItemClick(selectCategory: String) {
                // 하단 리사이클러뷰 변경하기
                getQuiz(selectCategory)
            }
        })
    }

    private fun getQuiz(category: String) {
        if(category == "모두") {
            // 어댑터 설정 후 전체 조회한 결과(quizAll) 추가
            myQuizAllAdapter = MyQuizAllAdapter()
            binding.itemMyquizQuizBoardsRv.adapter = myQuizAllAdapter
            binding.itemMyquizQuizBoardsRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            myQuizAllAdapter.addMyQuizAll(quizAll)
        }
        else {
            // 어댑터 설정 후 카테고리마다 결과 추가
            myQuizAdapter = MyQuizBoardAdapter(category)
            binding.itemMyquizQuizBoardsRv.adapter = myQuizAdapter
            binding.itemMyquizQuizBoardsRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

            val myQuizCateService = MyQuizCateService(this)
            myQuizCateService.getMyQuizCate(jwt, userIdx, category)
        }
    }

    override fun onGetMyQuizCateResultSuccess(result: ArrayList<MyQuiz>?) {
        if(result != null) {
            myQuizAdapter.addMyQuizCate(result)
        }
    }

    override fun onGetMyQuizCateResultFailure(message: String) {
        Log.d(ContentValues.TAG, "내 퀴즈 조회 실패 - $message")
    }

}
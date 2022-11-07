package com.cmccx.moge.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmccx.moge.data.remote.api.CategoryView
import com.cmccx.moge.data.remote.api.HomeInterestedBoardView
import com.cmccx.moge.data.remote.api.HomeInterestedCateApiJ
import com.cmccx.moge.data.remote.api.HomeInterestedCateApiW
import com.cmccx.moge.data.remote.model.HomeBoardResponse
import com.cmccx.moge.data.remote.model.HomeCateResponse
import com.cmccx.moge.data.remote.model.QuizBoard
import com.cmccx.moge.data.remote.model.QuizCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    enum class HomeApiStatus { LOADING, ERROR, DONE }

    // api 연결 상태
    private val _apiStatus = MutableLiveData<HomeApiStatus>()
    val apiStatus: LiveData<HomeApiStatus> = _apiStatus

    // 퀴즈 관련 인덱스
    private var _cateIdx = MutableLiveData<Int>()
    val cateIdx: LiveData<Int> = _cateIdx

    // 유저 관심 카테고리
    private var _quizCate = MutableLiveData<List<QuizCategory>>()
    val quizCate: LiveData<List<QuizCategory>> = _quizCate

    private var _quizBoard = MutableLiveData<List<QuizBoard>>()
    val quizBoard: LiveData<List<QuizBoard>> = _quizBoard

    init {
        Log.d("TEST-카테고리", "뷰모델 진입")
        /** 임시 **/
        _cateIdx.value = 1
    }

    // API 통신 -> 퀴즈 카테고리 가져오기
    fun getCategory(categoryView: CategoryView, jwt: String, userIdx: Int) {
        HomeInterestedCateApiJ.retrofitService.getCategory(jwt = jwt, userIdx = userIdx).enqueue(object :
            Callback<HomeCateResponse> {
            override fun onResponse(
                call: Call<HomeCateResponse>,
                response: Response<HomeCateResponse>
            ) {
                if (response.isSuccessful) {
                    _quizCate.value = response.body()?.result
                    categoryView.onGetCategoryResultSuccess()
                    Log.d("TEST-카테고리-카테", _quizCate.value.toString())
                } else {
                    Log.d("TEST-카테고리-카테", "response fail")
                }
            }

            override fun onFailure(call: Call<HomeCateResponse>, t: Throwable) {
                Log.d("TEST-카테고리-카테", "${t.message}")
            }

        })
    }

    // API 통신 -> 퀴즈 보드 가져오기
    fun getBoard(boardView: HomeInterestedBoardView, categoryIdx: Int) {
        HomeInterestedCateApiW.retrofitService.getBoard(categoryIdx = categoryIdx).enqueue(object :
        Callback<HomeBoardResponse> {
            override fun onResponse(
                call: Call<HomeBoardResponse>,
                response: Response<HomeBoardResponse>
            ) {
                if (response.isSuccessful) {
                    _quizBoard.value = response.body()?.result
                    boardView.onGetBoardSuccess()
                    Log.d("TEST-카테고리-보드", _quizCate.value.toString())
                } else {
                    Log.d("TEST-카테고리-보드", "response fail")
                }
            }

            override fun onFailure(call: Call<HomeBoardResponse>, t: Throwable) {
                Log.d("TEST-카테고리-보드", "${t.message}")
            }

        })
    }

}
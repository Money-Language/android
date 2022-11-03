package com.cmccx.moge.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmccx.moge.data.remote.api.HomeInterestedCateApi
import com.cmccx.moge.data.remote.model.QuizBoard
import com.cmccx.moge.data.remote.model.QuizCategory
import kotlinx.coroutines.launch

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
        Log.d("카테고리", "뷰모델 진입")
        /** 임시 **/
        _cateIdx.value = 1

        getCategory(_cateIdx.value!!)
    }

    // API 통신 -> 퀴즈 카테고리 가져오기
    private fun getCategory(categoryIdx: Int) {
        viewModelScope.launch {
            _apiStatus.value = HomeApiStatus.LOADING
            try {
                val response = HomeInterestedCateApi.retrofitService.getCateName(categoryIdx = categoryIdx)
                _quizCate.value = response.result
                Log.d("카테고리-카테", _quizCate.value.toString())

                _apiStatus.value = HomeApiStatus.DONE
            } catch (e: Exception) {
                Log.d("카테고리-카테", e.toString())
                _apiStatus.value = HomeApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 보드 가져오기
    fun getBoard(categoryIdx: Int) {
        viewModelScope.launch {
            _apiStatus.value = HomeApiStatus.LOADING
            try {
                val response = HomeInterestedCateApi.retrofitService.getBoard(categoryIdx = categoryIdx)
                _quizBoard.value = response.result
                Log.d("카테고리-보드", _quizCate.value.toString())

                _apiStatus.value = HomeApiStatus.DONE
            } catch (e: Exception) {
                Log.d("카테고리-보드", e.toString())
                _apiStatus.value = HomeApiStatus.ERROR
            }
        }
    }

}
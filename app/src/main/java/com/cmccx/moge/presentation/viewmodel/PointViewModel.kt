package com.cmccx.moge.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmccx.moge.data.remote.api.PointApi
import kotlinx.coroutines.launch

class PointViewModel: ViewModel() {
    enum class PointApiStatus { LOADING, ERROR, DONE }

    // api 연결 상태
    private val _apiStatus = MutableLiveData<PointApiStatus>()
    val apiStatus: LiveData<PointApiStatus> = _apiStatus

    private var _point = MutableLiveData<Int>()
    val point: LiveData<Int> = _point

    // API 통신 -> 퀴즈 조회수 상승
    fun getPoint(jwt: String, userIdx: Int) {
        viewModelScope.launch {
            _apiStatus.value = PointApiStatus.LOADING
            try {
                val response = PointApi.retrofitService.getPoint(
                    jwt = jwt,
                    userIdx = userIdx
                )
                _point.value = response.result[0].userPoint

                Log.d("TEST-포인트 조회", response.result[0].toString())
                _apiStatus.value = PointApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-포인트 조회", e.toString())
                _apiStatus.value = PointApiStatus.ERROR
            }
        }
    }
}
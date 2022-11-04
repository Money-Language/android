package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.UserResult

interface LoginView {
    fun onGetLoginResultSuccess(result: UserResult)
    fun onGetLoginResultFailure(message: String)
}

interface KakaoLoginView {
    fun onGetKakaoLoginResultSuccess(result: UserResult)
    fun onGetKakaoLoginResultFailure(message: String)
}

interface NaverLoginView {
    fun onGetNaverLoginResultSuccess(result: UserResult)
    fun onGetNaverLoginResultFailure(message: String)
}
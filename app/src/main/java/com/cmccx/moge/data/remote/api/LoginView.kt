package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.User

interface LoginView {
    fun onGetLoginResultSuccess(result: User)
    fun onGetLoginResultFailure(code:Int, message: String)
}

interface KakaoLoginView {
    fun onGetKakaoLoginResultSuccess(result: User)
    fun onGetKakaoLoginResultFailure(message: String)
}

interface NaverLoginView {
    fun onGetNaverLoginResultSuccess(result: User)
    fun onGetNaverLoginResultFailure(message: String)
}
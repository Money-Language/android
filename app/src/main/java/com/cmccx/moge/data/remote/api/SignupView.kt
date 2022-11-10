package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.SnsSignupResult
import com.cmccx.moge.data.remote.model.UserResult

interface SignupView {
    fun onGetSignUpResultSuccess(result: UserResult)
    fun onGetSignUpResultFailure(message: String)
}

interface KakaoSignupView {
    fun onGetKakaoSignUpResultSuccess(result: SnsSignupResult)
    fun onGetKakaoSignUpResultFailure(message: String)
}

interface NaverSignupView {
    fun onGetNaverSignUpResultSuccess(result: SnsSignupResult)
    fun onGetNaverSignUpResultFailure(message: String)
}
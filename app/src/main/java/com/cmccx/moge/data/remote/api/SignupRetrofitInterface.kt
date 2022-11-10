package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.Signup
import com.cmccx.moge.data.remote.model.SnsSignup
import com.cmccx.moge.data.remote.model.SnsSignupResponse
import com.cmccx.moge.data.remote.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupRetrofitInterface {
    @POST("/app/users/sign-up")
    fun sendSignup(@Body signup: Signup): Call<UserResponse>
}

interface KakaoSignupRetrofitInterface {
    @POST("/app/users/sign-up/kakao")
    fun sendKakaoSignup(@Body snsSignup: SnsSignup): Call<SnsSignupResponse>
}

interface NaverSignupRetrofitInterface {
    @POST("/app/users/sign-up/naver")
    fun sendNaverSignup(@Body snsSignup: SnsSignup): Call<SnsSignupResponse>
}
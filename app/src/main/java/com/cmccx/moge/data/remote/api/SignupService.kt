package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.data.remote.model.Signup
import com.cmccx.moge.data.remote.model.SnsSignup
import com.cmccx.moge.data.remote.model.SnsSignupResponse
import com.cmccx.moge.data.remote.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupService(val signupView: SignupView) {
    fun signUp(signup: Signup){
        val signUpService = ApplicationClass.retrofitJ.create(SignupRetrofitInterface::class.java)

        signUpService.sendSignup(signup).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> resp.result?.let { signupView.onGetSignUpResultSuccess(resp.result) }
                    else-> resp.message.let { signupView.onGetSignUpResultFailure(it) }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                signupView.onGetSignUpResultFailure(t.message ?: "통신 오류")
            }

        })
    }
}

class KakaoSignupService(val KakaoSignupView: KakaoSignupView) {
    fun kakaoSignUp(snsSignup: SnsSignup){
        val KakaoSignUpService = ApplicationClass.sWannyRetrofit.create(KakaoSignupRetrofitInterface::class.java)

        KakaoSignUpService.sendKakaoSignup(snsSignup).enqueue(object : Callback<SnsSignupResponse> {
            override fun onResponse(call: Call<SnsSignupResponse>, response: Response<SnsSignupResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> resp.result?.let { KakaoSignupView.onGetKakaoSignUpResultSuccess(resp.result) }
                    else-> resp.message.let { KakaoSignupView.onGetKakaoSignUpResultFailure(it) }
                }
            }

            override fun onFailure(call: Call<SnsSignupResponse>, t: Throwable) {
                KakaoSignupView.onGetKakaoSignUpResultFailure(t.message ?: "통신 오류")
            }

        })
    }
}

class NaverSignupService(val naverSignupView: NaverSignupView) {
    fun naverSignUp(snsSignup: SnsSignup){
        val naverSignUpService = ApplicationClass.sWannyRetrofit.create(NaverSignupRetrofitInterface::class.java)

        naverSignUpService.sendNaverSignup(snsSignup).enqueue(object : Callback<SnsSignupResponse> {
            override fun onResponse(call: Call<SnsSignupResponse>, response: Response<SnsSignupResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> resp.result?.let { naverSignupView.onGetNaverSignUpResultSuccess(resp.result) }
                    else-> resp.message.let { naverSignupView.onGetNaverSignUpResultFailure(it) }
                }
            }

            override fun onFailure(call: Call<SnsSignupResponse>, t: Throwable) {
                naverSignupView.onGetNaverSignUpResultFailure(t.message ?: "통신 오류")
            }

        })
    }
}
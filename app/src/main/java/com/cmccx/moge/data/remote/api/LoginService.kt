package com.cmccx.moge.data.remote.api

import android.util.Log
import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.data.remote.model.Login
import com.cmccx.moge.data.remote.model.SnsLogin
import com.cmccx.moge.data.remote.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val loginView: LoginView) {
    fun login(login: Login){
        val loginService = ApplicationClass.retrofitJ.create(LoginRetrofitInterface::class.java)

        loginService.sendLogin(login).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> loginView.onGetLoginResultSuccess(resp.result)
                    else-> loginView.onGetLoginResultFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("로그인 실패", t.message ?: "통신 오류")
            }

        })
    }
}

class KakaoLoginService(val kakaoLoginView: KakaoLoginView) {
    fun kakaoLogin(kakaoLogin: SnsLogin){
        val kakaoLoginService = ApplicationClass.sWannyRetrofit.create(KakaoLoginRetrofitInterface::class.java)

        kakaoLoginService.sendKakaoLogin(kakaoLogin).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> resp.result?.let { kakaoLoginView.onGetKakaoLoginResultSuccess(resp.result) }
                    else-> resp.message.let { kakaoLoginView.onGetKakaoLoginResultFailure(it) }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                kakaoLoginView.onGetKakaoLoginResultFailure(t.message ?: "통신 오류")
            }

        })
    }
}

class NaverLoginService(val naverLoginView: NaverLoginView) {
    fun naverLogin(naverLogin: SnsLogin){
        val naverLoginService = ApplicationClass.sWannyRetrofit.create(NaverLoginRetrofitInterface::class.java)

        naverLoginService.sendNaverLogin(naverLogin).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1000-> resp.result?.let { naverLoginView.onGetNaverLoginResultSuccess(resp.result) }
                    else-> resp.message.let { naverLoginView.onGetNaverLoginResultFailure(it) }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                naverLoginView.onGetNaverLoginResultFailure(t.message ?: "통신 오류")
            }

        })
    }
}

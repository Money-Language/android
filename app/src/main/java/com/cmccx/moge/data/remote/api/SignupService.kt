package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.data.remote.model.Signup
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

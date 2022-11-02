package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.data.remote.model.Login
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
                    1000-> resp.result?.let { loginView.onGetLoginResultSuccess(resp.result) }
                    else-> resp.message.let { loginView.onGetLoginResultFailure(it) }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                loginView.onGetLoginResultFailure(t.message ?: "통신 오류")
            }

        })
    }
}

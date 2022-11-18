package com.cmccx.moge.data.remote.api

import android.util.Log
import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.base.BaseResponse
import com.cmccx.moge.data.remote.model.Password
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailValidationService(val emailValidationView: EmailValidationView) {
    fun getEmailValidation(email: String){
        val emailValidationService = ApplicationClass.retrofitJ.create(EmailValidationRetrofitInterface::class.java)

        emailValidationService.emailValidation(email).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1017-> emailValidationView.onGetEmailValidationResultSuccess()
                    else-> emailValidationView.onGetEmailValidationResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("이메일 검증 실패", t.message ?: "통신 오류")
            }

        })
    }
}

class NicknameValidationService(val nicknameValidationView: NicknameValidationView) {
    fun getNicknameValidation(nickname: String){
        val nicknameValidationService = ApplicationClass.retrofitJ.create(NicknameValidationRetrofitInterface::class.java)

        nicknameValidationService.nicknameValidation(nickname).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1016-> nicknameValidationView.onGetNicknameValidationResultSuccess()
                    else-> nicknameValidationView.onGetNicknameValidationResultFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("닉네임 검증 실패", t.message ?: "통신 오류")
            }

        })
    }
}

class PasswordValidationService(val passwordValidationView: PasswordValidationView) {
    fun getPasswordValidation(password: Password){
        val passwordValidationService = ApplicationClass.retrofitJ.create(PasswordValidationRetrofitInterface::class.java)

        passwordValidationService.passwordValidation(password).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                val resp = response.body()!!
                when(resp.code){
                    1018-> passwordValidationView.onGetPasswordValidationResultSuccess()
                    else-> passwordValidationView.onGetPasswordValidationResultFailure(resp.code, resp.message)
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d("비밀번호 검증 실패", t.message ?: "통신 오류")
            }

        })
    }
}
package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.BaseResponse
import com.cmccx.moge.data.remote.model.Password
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

// 이메일 확인 API
interface EmailValidationRetrofitInterface {
    @POST("/app/users/validate-email")
    fun emailValidation(@Query("email") type: String): Call<BaseResponse>
}

// 닉네임 확인 API
interface NicknameValidationRetrofitInterface {
    @POST("/app/users/validate-nickname")
    fun nicknameValidation(@Query("nickname") type: String): Call<BaseResponse>
}

// 비밀번호 확인 API
interface PasswordValidationRetrofitInterface {
    @POST("/app/users/validate-password")
    fun passwordValidation(@Body password: Password): Call<BaseResponse>
}
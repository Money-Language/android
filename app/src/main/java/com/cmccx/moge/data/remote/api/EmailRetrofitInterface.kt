package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.BaseResponse
import com.cmccx.moge.data.remote.model.Code
import com.cmccx.moge.data.remote.model.CodeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

// 이메일 전송 API
interface EmailRetrofitInterface {
    @POST("/app/users/send-email")
    fun sendEmail(@Query("email") type: String): Call<BaseResponse>
}

// 이메일 인증 코드 전송 API
interface CodeRetrofitInterface {
    @POST("/app/users/login/check-email")
    fun sendCode(@Body code: Code): Call<CodeResponse>
}
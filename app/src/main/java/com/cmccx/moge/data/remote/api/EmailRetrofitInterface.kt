package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.BaseResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface EmailRetrofitInterface {
    @POST("/app/users/send-email")
    fun sendEmail(@Query("email") type: String): Call<BaseResponse>
}
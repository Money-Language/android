package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.Code
import com.cmccx.moge.data.remote.model.CodeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CodeRetrofitInterface {
    @POST("/app/users/login/check-email")
    fun sendCode(@Body code: Code): Call<CodeResponse>
}
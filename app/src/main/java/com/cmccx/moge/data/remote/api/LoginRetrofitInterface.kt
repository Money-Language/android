package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.Login
import com.cmccx.moge.data.remote.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("/app/users/login")
    fun sendLogin(@Body login: Login): Call<UserResponse>
}
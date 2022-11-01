package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.Signup
import com.cmccx.moge.data.remote.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupRetrofitInterface {
    @POST("/app/users/sign-up")
    fun sendSignup(@Body signup: Signup): Call<UserResponse>
}
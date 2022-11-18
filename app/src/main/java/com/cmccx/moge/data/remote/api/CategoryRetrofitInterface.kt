package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.BaseResponse
import com.cmccx.moge.data.remote.model.Category
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryRetrofitInterface {
    // 카테고리 설정
    @POST("/app/users/{userIdx}/keyword")
    fun sendCategory(
        @Header("X-ACCESS-TOKEN") jwt : String, // jwt
        @Path("userIdx") userIdx : Int,
        @Body category : Category,
    ): Call<BaseResponse>

    // 카테고리 수정
    @PATCH("/app/users/{userIdx}/keyword")
    fun modCategory(
        @Header("X-ACCESS-TOKEN") jwt : String, // jwt
        @Path("userIdx") userIdx : Int,
        @Body category : Category,
    ): Call<BaseResponse>
}
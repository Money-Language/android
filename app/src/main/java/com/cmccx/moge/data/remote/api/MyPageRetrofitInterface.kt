package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.MyFavResponse
import com.cmccx.moge.data.remote.model.MyQuizResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MyPageRetrofitInterface {
    // 유저가 좋아요 누른 게시글 조회
    @GET("/app/users/{userIdx}/board/like")
    fun getMyFav(
        @Header("X-ACCESS-TOKEN") jwt : String, // jwt
        @Path("userIdx") userIdx : Int,
    ): Call<MyFavResponse>

    // 유저가 작성한 게시글 전체 조회
    @GET("/app/users/{userIdx}/boards")
    fun getMyQuizAll(
        @Header("X-ACCESS-TOKEN") jwt : String, // jwt
        @Path("userIdx") userIdx : Int,
    ): Call<MyQuizResponse>

    // 유저가 작성한 게시글 카테고리 별로 조회
    @GET("/app/users/{userIdx}/boards")
    fun getMyQuizCate(
        @Header("X-ACCESS-TOKEN") jwt : String, // jwt
        @Path("userIdx") userIdx : Int,
        @Query("categoryName") type: String,
    ): Call<MyQuizResponse>
}
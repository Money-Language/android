package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.HomeBoardResponse
import com.cmccx.moge.data.remote.model.HomeCateResponse
import com.cmccx.moge.data.remote.model.HomeFollowingProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApiService {
    // 피드 프로필 조회 - 지니
    @GET("/app/users/{userIdx}/following?")
    suspend fun getFollowingProfile(
        @Header("X-ACCESS-TOKEN") jwt : String,
        @Path("userIdx") userIdx: Int,
        @Query("page") page: Int
    ): HomeFollowingProfileResponse

    // 카테고리 별 타이틀, 서브타이틀 조회 - 지니
    @GET("/app/users/{userIdx}/keyword")
    fun getCategory(
        @Header("X-ACCESS-TOKEN") jwt : String,
        @Path("userIdx") userIdx: Int
    ): Call<HomeCateResponse>

    // 보드 조회 - 와니
    @GET("/app/boards/{categoryIdx}")
    fun getBoard(
        @Path("categoryIdx") categoryIdx: Int
    ): Call<HomeBoardResponse>
}
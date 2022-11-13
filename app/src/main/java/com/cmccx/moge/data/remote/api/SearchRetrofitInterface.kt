package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.BaseResponse
import com.cmccx.moge.data.remote.model.BoardResponse
import com.cmccx.moge.data.remote.model.TopTenResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchRetrofitInterface {
    // TODO 검색 조회 API
    @GET("검색 조회 URL 입력")
    fun getSearch(): Call<BaseResponse>
}

interface TopTenRetrofitInterface {
    // 조회수 top 10
    @GET("/app/boards/top-view")
    fun getTopView(): Call<TopTenResponse>

    // 좋아요 top 10
    @GET("/app/boards/top-like")
    fun getTopLike(): Call<TopTenResponse>
}

interface QuizTenRetrofitInterface {
    // 카테고리 최신순
    @GET("/app/boards/{categoryIdx}")
    fun getQuizLatest (
        @Header("X-ACCESS-TOKEN") jwt : String,
        @Path("categoryIdx") categoryIdx: Int
    ): Call<BoardResponse>

    // 조회순, 인기순
    @GET("/app/boards/{categoryIdx}")
    fun getQuizOrder (
        @Header("X-ACCESS-TOKEN") jwt : String,
        @Path("categoryIdx") categoryIdx: Int,
        @Query("order") type: String
    ): Call<BoardResponse>

}
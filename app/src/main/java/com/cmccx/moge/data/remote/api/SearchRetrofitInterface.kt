package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.BoardResponse
import com.cmccx.moge.data.remote.model.KeywordResponse
import com.cmccx.moge.data.remote.model.SearchResponse
import com.cmccx.moge.data.remote.model.TopTenResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchRetrofitInterface {
    // 검색 결과
    @GET("/app/boards/search")
    fun getSearch(
        @Query("title") type: String?
    ): Call<SearchResponse>
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

interface KeywordRetrofitInterface {
    // 추천 키워드
    @GET("app/boards/search-keyword")
    fun getKeyword(): Call<KeywordResponse>
}
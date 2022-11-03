package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.HomeBoardResponse
import com.cmccx.moge.data.remote.model.HomeCateResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeInterestedCateApiService {
    // 카테고리 별 타이틀, 서브타이틀 조회
    @GET("/app/users/boards/category-name/{categoryIdx}")
    suspend fun getCateName(
        @Path("categoryIdx") categoryIdx: Int
    ): HomeCateResponse

    // 보드 조회
    @GET("/app/users/boards/{categoryIdx}")
    suspend fun getBoard(
        @Path("categoryIdx") categoryIdx: Int
    ): HomeBoardResponse
}
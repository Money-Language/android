package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.PointResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PointApiService {
    // 유저의 포인트 조회 - 와니
    @GET("/app/users/{userIdx}/points")
    suspend fun getPoint(
        @Header("X-ACCESS-TOKEN") jwt : String,
        @Path("userIdx") userIdx: Int
    ): PointResponse
}
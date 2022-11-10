package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.BaseResponse
import com.cmccx.moge.data.remote.model.TopTenResponse
import retrofit2.Call
import retrofit2.http.GET

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
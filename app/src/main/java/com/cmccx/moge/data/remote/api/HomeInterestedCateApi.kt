package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.ApplicationClass

object HomeInterestedCateApi {
    val retrofitService : HomeInterestedCateApiService by lazy {
        ApplicationClass.sWannyRetrofit.create(HomeInterestedCateApiService::class.java)
    }
}
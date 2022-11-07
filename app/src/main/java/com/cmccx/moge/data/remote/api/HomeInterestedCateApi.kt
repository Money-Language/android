package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.ApplicationClass

object HomeInterestedCateApiW {
    val retrofitService : HomeInterestedCateApiService by lazy {
        ApplicationClass.sWannyRetrofit.create(HomeInterestedCateApiService::class.java)
    }
}

object HomeInterestedCateApiJ {
    val retrofitService : HomeInterestedCateApiService by lazy {
        ApplicationClass.retrofitJ.create(HomeInterestedCateApiService::class.java)
    }
}
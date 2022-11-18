package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.ApplicationClass

object HomeInterestedCateApiW {
    val retrofitService : HomeApiService by lazy {
        ApplicationClass.sWannyRetrofit.create(HomeApiService::class.java)
    }
}

object HomeInterestedCateApiJ {
    val retrofitService : HomeApiService by lazy {
        ApplicationClass.retrofitJ.create(HomeApiService::class.java)
    }
}
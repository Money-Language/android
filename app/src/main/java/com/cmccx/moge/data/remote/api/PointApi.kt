package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.ApplicationClass

object PointApi {
    val retrofitService : PointApiService by lazy {
        ApplicationClass.sWannyRetrofit.create(PointApiService::class.java)
    }
}
package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.ApplicationClass.Companion.sWannyRetrofit

object QuizApi {
    val retrofitService : QuizApiService by lazy {
        sWannyRetrofit.create(QuizApiService::class.java)
    }
}
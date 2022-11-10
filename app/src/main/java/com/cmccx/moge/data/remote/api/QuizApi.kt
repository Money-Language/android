package com.cmccx.moge.data.remote.api

import com.cmccx.moge.base.ApplicationClass.Companion.retrofitJ
import com.cmccx.moge.base.ApplicationClass.Companion.sWannyRetrofit

object QuizApiWanny {
    val retrofitService : QuizApiService by lazy {
        sWannyRetrofit.create(QuizApiService::class.java)
    }
}

object QuizApiJinny {
    val retrofitService : QuizApiService by lazy {
        retrofitJ.create(QuizApiService::class.java)
    }
}
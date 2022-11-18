package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.MyQuiz

interface MyQuizAllView {
    fun onGetMyQuizAllResultSuccess(result: ArrayList<MyQuiz>?)
    fun onGetMyQuizAllResultFailure(code:Int, message: String)
}

interface MyQuizCateView {
    fun onGetMyQuizCateResultSuccess(result: ArrayList<MyQuiz>?)
    fun onGetMyQuizCateResultFailure(message: String)
}

interface MyFavView {
    fun onModMyFavResultSuccess()
    fun onModMyFavResultFailure(message: String)
}
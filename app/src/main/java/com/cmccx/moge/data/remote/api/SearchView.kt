package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.Board
import com.cmccx.moge.data.remote.model.TopTen

interface SearchView {
    fun onGetSearchResultSuccess()
    fun onGetSearchResultFailure(message: String)
}

interface TopView {
    fun onGetTopViewResultSuccess(result: ArrayList<TopTen>)
    fun onGetTopViewResultFailure(message: String)
}

interface TopLike {
    fun onGetTopLikeResultSuccess(result: ArrayList<TopTen>)
    fun onGetTopLikeResultFailure(message: String)
}

interface QuizOrderView {
    fun onGetQuizOrderResultSuccess(result: ArrayList<Board>)
    fun onGetQuizOrderResultFailure(message: String)
}
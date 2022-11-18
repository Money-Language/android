package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.Board
import com.cmccx.moge.data.remote.model.Keyword
import com.cmccx.moge.data.remote.model.Search

interface QueryView {
    fun onGetSearchResultSuccess(result: ArrayList<Search>?)
    fun onGetSearchResultFailure(code: Int, message: String)
}

interface TopView {
    fun onGetTopViewResultSuccess(result: ArrayList<Search>)
    fun onGetTopViewResultFailure(message: String)
}

interface TopLike {
    fun onGetTopLikeResultSuccess(result: ArrayList<Search>)
    fun onGetTopLikeResultFailure(message: String)
}

interface QuizOrderView {
    fun onGetQuizOrderResultSuccess(result: ArrayList<Board>)
    fun onGetQuizOrderResultFailure(message: String)
}

interface KeywordView {
    fun onGetKeywordResultSuccess(result: ArrayList<Keyword>)
    fun onGetKeywordResultFailure(message: String)
}
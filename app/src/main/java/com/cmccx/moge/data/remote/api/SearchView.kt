package com.cmccx.moge.data.remote.api

interface SearchView {
    fun onGetSearchResultSuccess()
    fun onGetSearchResultFailure(message: String)
}

interface TopViewView {
    fun onGetTopViewResultSuccess()
    fun onGetTopViewResultFailure(message: String)
}

interface TopLikeView {
    fun onGetTopLikeResultSuccess()
    fun onGetTopLikeResultFailure(message: String)
}
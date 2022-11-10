package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.TopTen

interface SearchView {
    fun onGetSearchResultSuccess()
    fun onGetSearchResultFailure(message: String)
}

interface TopViewView {
    fun onGetTopViewResultSuccess(result: ArrayList<TopTen>)
    fun onGetTopViewResultFailure(message: String)
}

interface TopLikeView {
    fun onGetTopLikeResultSuccess(result: ArrayList<TopTen>)
    fun onGetTopLikeResultFailure(message: String)
}
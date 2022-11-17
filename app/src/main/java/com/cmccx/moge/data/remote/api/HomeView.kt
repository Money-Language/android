package com.cmccx.moge.data.remote.api

interface HomeView {
    fun onGetBoardSuccess()
    fun onGetBoardFailure(message: String)
}
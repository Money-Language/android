package com.cmccx.moge.data.remote.api

interface EmailView {
    fun onGetEmailResultSuccess()
    fun onGetEmailResultFailure(message: String)
}
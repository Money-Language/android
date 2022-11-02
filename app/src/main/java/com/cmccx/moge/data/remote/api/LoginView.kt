package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.UserResult

interface LoginView {
    fun onGetLoginResultSuccess(result: UserResult)
    fun onGetLoginResultFailure(message: String)
}
package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.UserResult

interface SignupView {
    fun onGetSignUpResultSuccess(result: UserResult)
    fun onGetSignUpResultFailure(message: String)
}
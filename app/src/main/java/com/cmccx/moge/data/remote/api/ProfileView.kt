package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.Profile

interface ProfileView {
    fun onGetProfileResultSuccess(result: Profile)
    fun onGetProfileResultFailure(message: String)
}

interface ModProfileView {
    fun onModProfileResultSuccess()
    fun onModProfileResultFailure(message: String)
}

interface DelProfileView {
    fun onDelProfileResultSuccess()
    fun onDelProfileResultFailure(message: String)
}

interface ModPasswordView {
    fun onModPasswordResultSuccess()
    fun onModPasswordResultFailure(message: String)
}
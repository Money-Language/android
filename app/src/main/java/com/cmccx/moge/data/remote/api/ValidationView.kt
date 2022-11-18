package com.cmccx.moge.data.remote.api

interface EmailValidationView {
    fun onGetEmailValidationResultSuccess()
    fun onGetEmailValidationResultFailure(message: String)
}

interface NicknameValidationView {
    fun onGetNicknameValidationResultSuccess()
    fun onGetNicknameValidationResultFailure(message: String)
}

interface PasswordValidationView {
    fun onGetPasswordValidationResultSuccess()
    fun onGetPasswordValidationResultFailure(code: Int, message: String)
}
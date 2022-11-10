package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.CodeResult

interface EmailView {
    fun onGetEmailResultSuccess()
    fun onGetEmailResultFailure(message: String)
}

interface CodeView {
    fun onGetCodeResultSuccess(result: CodeResult)
    fun onGetCodeResultFailure(message: String)
}

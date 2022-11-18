package com.cmccx.moge.data.remote.model

import com.cmccx.moge.base.BaseResponse

data class QuizQuestionResponse (
    val result: List<QuizQuestion>
) : BaseResponse()

interface QuizQuestionResult {
    fun onGetQuizQuestionResultSuccess()
    fun onGetQuizQuestionResultFailure(message: String)
}

data class QuizChoiceResponse (
    val result: List<QuizChoice>
) : BaseResponse()

interface QuizChoiceResult {
    fun onGetQuizChoiceResultSuccess()
    fun onGetQuizChoiceResultFailure(message: String)
}

data class QuizCommentResponse (
    val result: List<QuizComment>
) : BaseResponse()
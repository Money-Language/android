package com.cmccx.moge.data.remote.model

data class QuizQuestionResponse (
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: List<QuizQuestion>
)

data class QuizChoiceResponse (
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: List<QuizChoice>
)

data class QuizAnswerResponse (
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: QuizAnswer
)
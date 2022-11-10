package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class QuizAnswer(
    @SerializedName("boardIdx")         val boardIdx: Int,                  // 보드 인덱스
    @SerializedName("quizIdx")          val quizIdx: Int,                   // 퀴즈 인덱스
    @SerializedName("answerSelectIdx")  val quizChoiceIdx: String,          // 보기 인덱스
    @SerializedName("answer")           val quizAnswerValue: String,        // 정답 값
    @SerializedName("answerCorrect")    val isQuizAnswer: String,           // 정답 여부
)

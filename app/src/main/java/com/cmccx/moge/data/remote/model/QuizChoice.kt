package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class QuizChoice(
    @SerializedName("boardIdx")         val boardIdx: Int,              // 보드 인덱스
    @SerializedName("quizIdx")          val quizIdx: Int,               // 퀴즈 인덱스
    @SerializedName("answerSelectIdx")  val choiceIdx: String,             // 보기 인덱스
    @SerializedName("subjectiveHint")   val quizHint: String,           // 주관식일 경우, 퀴즈 힌트
    @SerializedName("answer")           val quizChoice: String          // 문제 보기
)

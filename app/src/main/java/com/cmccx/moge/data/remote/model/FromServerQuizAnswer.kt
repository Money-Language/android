package com.cmccx.moge.data.remote.model

import com.google.gson.annotations.SerializedName

data class FromServerQuizAnswer(
    @SerializedName("boardIdx")         val boardIdx: Int,
    @SerializedName("quizIdx")          val quizIdx: Int,
    @SerializedName("subjectiveHint")   val choiceHint: String,     // 주관식 힌트
    @SerializedName("answer")           val choice: String,         // 보기
    @SerializedName("answerSelectIdx")  val choiceIdx: String,      // 보기 인덱스
    @SerializedName("objectiveAnswer")  val answer: String          // 정답
)

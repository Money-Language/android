package com.cmccx.moge.data.remote.model

data class Quiz(
    val boardIdx: Int,
    val quizIdx: Int,
    val quizType: String,          // 퀴즈 분류 - 객관식, 주관식
    val quizQuestion: String,      // 퀴즈 문제
    val choiceHint: String,        // 주관식 힌트
    val choiceFirstIdx: String,    // 보기1 인덱스
    val choiceFirst: String,       // 보기1
    val choiceSecondIdx: String,   // 보기2 인덱스
    val choiceSecond: String,      // 보기2
    val quizAnswer: String         // 정답
)

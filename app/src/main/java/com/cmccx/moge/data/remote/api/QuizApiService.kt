package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.QuizAnswer
import com.cmccx.moge.data.remote.model.QuizChoice
import com.cmccx.moge.data.remote.model.QuizQuestion
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuizApiService {
    // 퀴즈 문제 조회
    @GET("/app/boards/{boardIdx}/quiz")
    suspend fun getQuizQuestion(
        @Path("boardIdx") boardIdx: Int
    ): List<QuizQuestion>

    // 퀴즈 보기 조회
    @GET("/app/boards/{boardIdx}/quiz?quizIdx=")
    suspend fun getQuizChoice(
        @Path("boardIdx") boardIdx: Int,
        @Query("quizIdx") quizIdx: Int
    ): List<QuizChoice>

    // 퀴즈 정답 조회
    @GET("/app/boards/{boardIdx}/quiz?quizIdx=&answerSelectIdx=")
    suspend fun getQuizAnswer(
        @Path("boardIdx") boardIdx: Int,
        @Query("quizIdx") quizIdx: Int,
        @Query("answerSelectIdx") quizChoiceIdx: String
    ): List<QuizAnswer>
}
package com.cmccx.moge.data.remote.api

import com.cmccx.moge.data.remote.model.QuizAnswerResponse
import com.cmccx.moge.data.remote.model.QuizChoiceResponse
import com.cmccx.moge.data.remote.model.QuizQuestionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuizApiService {
    // 퀴즈 문제 조회
    @GET("/app/boards/{boardIdx}/quiz")
    suspend fun getQuizQuestion(
        @Path("boardIdx") boardIdx: Int
    ): QuizQuestionResponse

    // 퀴즈 보기 조회
    @GET("/app/boards/{boardIdx}/quiz?")
    suspend fun getQuizChoice(
        @Path("boardIdx") boardIdx: Int,
        @Query("quizIdx") quizIdx: Int
    ): QuizChoiceResponse

    // 퀴즈 정답 조회
    @GET("/app/boards/{boardIdx}/quiz?")
    suspend fun getQuizAnswer(
        @Path("boardIdx") boardIdx: Int,
        @Query("quizIdx") quizIdx: Int,
        @Query("answerSelectIdx") quizChoiceIdx: String
    ): QuizAnswerResponse
}
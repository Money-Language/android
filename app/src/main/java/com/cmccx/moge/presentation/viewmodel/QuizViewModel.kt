package com.cmccx.moge.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmccx.moge.data.remote.api.QuizApi
import com.cmccx.moge.data.remote.model.FromServerQuizAnswer
import com.cmccx.moge.data.remote.model.FromServerQuizQuestion
import com.cmccx.moge.data.remote.model.Quiz
import kotlinx.coroutines.launch

class QuizViewModel: ViewModel() {

    enum class QuizApiStatus { LOADING, ERROR, DONE }
    enum class QuizType { MULTI, SHORT }  // 객관식-MULTI, 주관식-SHORT
    enum class QuizTry { DONE, YET }

    // api 연결 상태
    private val _status = MutableLiveData<QuizApiStatus>()
    val status: LiveData<QuizApiStatus> = _status

    // 퀴즈 도전 상태
    private val _tryStatus = MutableLiveData<QuizTry>()
    val tryStatus: LiveData<QuizTry> = _tryStatus

    // 서버에서 받아오는 퀴즈 result 값
    private var _quizQuestion = listOf<FromServerQuizQuestion>()            // 퀴즈 문제
    private var _quizAnswer = listOf<FromServerQuizAnswer>()                // 퀴즈 보기, 정답

    // 퀴즈
    private val _quizList = MutableLiveData<List<Quiz>>()
    val quizList: LiveData<List<Quiz>> = _quizList

    private var _curQuizIdx = 0

    init {

    }

    // API 통신 -> 퀴즈 문제 가져오기
    fun getQuizList(boardIdx: Int) {
        viewModelScope.launch {
            _status.value = QuizApiStatus.LOADING
            try {
                _quizQuestion = QuizApi.retrofitService.getQuizQuestion(boardIdx = boardIdx)
                _status.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                _status.value = QuizApiStatus.ERROR
                _quizList.value = listOf()
            }
        }
    }

    // API 통신 -> 퀴즈 보기, 정답 가져오기
    fun getQuizAnswer(boardIdx: Int, quizIdx: Int) {
        viewModelScope.launch {
            _status.value = QuizApiStatus.LOADING
            try {
                _quizAnswer = QuizApi.retrofitService.getQuizAnswer(boardIdx = boardIdx, quizIdx = quizIdx)
                _status.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                _status.value = QuizApiStatus.ERROR
                _quizList.value = listOf()
            }
        }
    }

    // 포지션 인덱스 증가
    fun plusCurQuizIdx() {
        _curQuizIdx++
    }

    // 퀴즈 재조합
    private fun makeQuiz() {

    }

}
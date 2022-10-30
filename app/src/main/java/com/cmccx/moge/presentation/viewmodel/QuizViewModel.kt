package com.cmccx.moge.presentation.viewmodel

import androidx.lifecycle.*
import com.cmccx.moge.data.remote.api.QuizApi
import com.cmccx.moge.data.remote.model.QuizAnswer
import com.cmccx.moge.data.remote.model.QuizChoice
import com.cmccx.moge.data.remote.model.QuizQuestion
import kotlinx.coroutines.launch

class QuizViewModel: ViewModel() {

    enum class QuizApiStatus { LOADING, ERROR, DONE }
    enum class QuizType { MULTI, SHORT }  // 객관식-MULTI, 주관식-SHORT
    enum class QuizTry { DONE, YET }

    // api 연결 상태
    private val _apiStatus = MutableLiveData<QuizApiStatus>()
    val apiStatus: LiveData<QuizApiStatus> = _apiStatus

    // 퀴즈 도전 상태
    private val _tryStatus = MutableLiveData<QuizTry>()
    val tryStatus: LiveData<QuizTry> = _tryStatus

    // 서버에서 받아오는 퀴즈 result 값
    private var _quizQuestion = MutableLiveData<List<QuizQuestion>>()
    val quizQuestion: LiveData<List<QuizQuestion>> = _quizQuestion

    private var _quizChoice = MutableLiveData<List<QuizChoice>>()
    val quizChoice: LiveData<List<QuizChoice>> = _quizChoice

    private var _quizAnswer = MutableLiveData<List<QuizAnswer>>()
    val quizAnswer: LiveData<List<QuizAnswer>> = _quizAnswer


    init {

    }

    // API 통신 -> 퀴즈 문제 가져오기
    fun getQuizQuestion(boardIdx: Int) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                _quizQuestion.value = QuizApi.retrofitService.getQuizQuestion(boardIdx = boardIdx)
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 보기 가져오기
    fun getQuizChoice(boardIdx: Int, quizIdx:Int) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                _quizChoice.value = QuizApi.retrofitService.getQuizChoice(boardIdx = boardIdx, quizIdx = quizIdx)
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 정답 가져오기
    fun getQuizAnswer(boardIdx: Int, quizIdx: Int, quizChoiceIdx: String) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                _quizAnswer.value = QuizApi.retrofitService.getQuizAnswer(boardIdx = boardIdx, quizIdx = quizIdx, quizChoiceIdx = quizChoiceIdx)
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

}
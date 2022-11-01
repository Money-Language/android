package com.cmccx.moge.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.cmccx.moge.data.remote.api.QuizApi
import com.cmccx.moge.data.remote.model.Quiz
import com.cmccx.moge.data.remote.model.QuizAnswer
import com.cmccx.moge.data.remote.model.QuizChoice
import com.cmccx.moge.data.remote.model.QuizQuestion
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizViewModel: ViewModel() {

    enum class QuizApiStatus { LOADING, ERROR, DONE }
    enum class QuizTry { DONE, YET }
    enum class QuizResult { CORRECT, WRONG }    // 정답 여부

    // api 연결 상태
    private val _apiStatus = MutableLiveData<QuizApiStatus>()
    val apiStatus: LiveData<QuizApiStatus> = _apiStatus

    // 퀴즈 도전 상태
    private val _tryStatus = MutableLiveData<QuizTry>()
    val tryStatus: LiveData<QuizTry> = _tryStatus

    // 사용자의 정답 여부
    private val _tryResult = MutableLiveData<QuizResult>()
    val tryResult: LiveData<QuizResult> = _tryResult

    // 서버에서 받아오는 퀴즈 result 값
    private var _quizQuestion = MutableLiveData<List<QuizQuestion>>()
    val quizQuestion: LiveData<List<QuizQuestion>> = _quizQuestion

    private var _quizChoiceFirst = MutableLiveData<List<QuizChoice>>()
    val quizChoiceFirst: LiveData<List<QuizChoice>> = _quizChoiceFirst

    private var _quizChoiceSecond = MutableLiveData<List<QuizChoice>>()
    val quizChoiceSecond: LiveData<List<QuizChoice>> = _quizChoiceSecond

    private var _quizAnswer = MutableLiveData<QuizAnswer>()
    val quizAnswer: LiveData<QuizAnswer> = _quizAnswer

    // 퀴즈

    private var _quiz = MutableLiveData<List<Quiz>>()
    val quiz: LiveData<List<Quiz>> = _quiz
    private var _curPosition = 0

    // 사용자 선택
    private var _userBoard = MutableLiveData<Int>()
    private var _userQuiz = MutableLiveData<Int>()
    private var _userChoice = MutableLiveData<String>()


    init {
        /** !!! 보드 id 파셀라이즈 처리 해야함 !!! **/
        _userBoard.value = 1

        _tryStatus.value = QuizTry.YET

        getQuizQuestion(_userBoard.value!!)

        makeQuiz(_curPosition)

    }

    fun setBoardIdx(input: Int) {
        _userBoard.value = input
    }

    fun isTry(input: Boolean) {
        if (input) {
            _tryStatus.value = QuizTry.DONE
        } else {
            _tryStatus.value = QuizTry.YET
        }
    }

    fun isCorrect(input: Boolean) {
        if (input) {
            _tryResult.value = QuizResult.CORRECT
        } else {
            _tryResult.value = QuizResult.WRONG
        }
    }

    // API 통신 -> 퀴즈 문제 가져오기
    private fun getQuizQuestion(boardIdx: Int) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                val response = QuizApi.retrofitService.getQuizQuestion(boardIdx = boardIdx)
                _quizQuestion.value = response.result
                Log.d("TEST-문제", _quizQuestion.value.toString())

                // 보기 받아오기
                if (_quizQuestion.value!![_curPosition].quizType == "객관식") {
                    getQuizChoiceFirst(boardIdx = boardIdx, quizIdx = _curPosition+1)
                    getQuizChoiceSecond(boardIdx = boardIdx, quizIdx = _curPosition+1)
                    //getQuizAnswer(boardIdx = boardIdx, quizIdx = _curPosition+1, quizChoiceIdx = "01")
                } else {
                    getQuizChoiceFirst(boardIdx = boardIdx, quizIdx = _curPosition+1)
                    //getQuizAnswer(boardIdx = boardIdx, quizIdx = _curPosition+1, quizChoiceIdx = "01")
                }

                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-문제", e.toString())
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 보기 가져오기
    fun getQuizChoiceFirst(boardIdx: Int, quizIdx:Int) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                val response = QuizApi.retrofitService.getQuizChoice(boardIdx = boardIdx, quizIdx = quizIdx)
                _quizChoiceFirst.value = listOf(response.result[0])
                Log.d("TEST-보기1", response.result.toString())
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-보기1", e.toString())
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 보기 가져오기
    fun getQuizChoiceSecond(boardIdx: Int, quizIdx:Int) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                val response = QuizApi.retrofitService.getQuizChoice(boardIdx = boardIdx, quizIdx = quizIdx)
                _quizChoiceSecond.value = listOf(response.result[1])
                Log.d("TEST-보기2", response.result.toString())
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-보기2", e.toString())
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // API 통신 -> 퀴즈 정답 가져오기
    fun getQuizAnswer(boardIdx: Int, quizIdx: Int, quizChoiceIdx: String) {
        viewModelScope.launch {
            _apiStatus.value = QuizApiStatus.LOADING
            try {
                val response = QuizApi.retrofitService.getQuizAnswer(boardIdx = boardIdx, quizIdx = quizIdx, quizChoiceIdx = quizChoiceIdx)
                _quizAnswer.value = response.result
                Log.d("TEST-정답", response.result.toString())
                _apiStatus.value = QuizApiStatus.DONE
            } catch (e: Exception) {
                Log.d("TEST-정답", e.toString())
                _apiStatus.value = QuizApiStatus.ERROR
            }
        }
    }

    // 사용자가 퀴즈를 풀었을 때
    fun onClickQuizChoice(input: QuizTry, idx: String) {
        if (input == QuizTry.DONE) {
            _tryStatus.value = QuizTry.DONE

            _userBoard.value = _quiz.value!![_curPosition].boardIdx
            _userQuiz.value = _quiz.value!![_curPosition].quizIdx
            _userChoice.value = idx

            //getQuizAnswer(_userBoard.value!!, _userQuiz.value!!, _userChoice.value!!)

            _curPosition += 1
        } else {
            // !!!!!!!!!
        }
    }

    private fun makeQuiz(idx: Int) {
        viewModelScope.launch {
            delay(200)
            try {
                val test = Quiz(
                    boardIdx= 1,
                    quizIdx= idx+1,
                    quizTotal= _quizQuestion.value!!.size.toString(),           // 퀴즈 총 갯수
                    quizType= _quizQuestion.value!![idx].quizType,              // 퀴즈 분류 - 객관식, 주관식
                    quizQuestion= _quizQuestion.value!![idx].quizQuestion,      // 퀴즈 문제
                    choiceHint= _quizChoiceFirst.value!![0].quizChoice,         // 주관식 힌트
                    choiceFirstIdx= "_quizChoiceFirst.value!![0].choiceIdx",    // 보기1 인덱스
                    choiceFirst= _quizChoiceFirst.value!![0].quizChoice,        // 보기1
                    choiceSecondIdx= "_quizChoiceSecond.value!![0].choiceIdx",  // 보기2 인덱스
                    choiceSecond= _quizChoiceSecond.value!![0].quizChoice,      // 보기2
                    quizAnswer= "_quizAnswer.value!!.quizAnswerValue"           // 정답
                )

                _quiz.value = listOf(test)
                Log.d("TEST-완성", test.toString())
            } catch (e: Exception) {
                Log.d("TEST-완성", e.toString())
            }
        }
    }

}
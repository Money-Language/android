package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentEmailValidBinding
import java.util.regex.Pattern


// TODO 만약 사용자가 인증 후 뒤로가기 버튼 누르면, 인증 다시?
class EmailValidFragment : BaseFragment<FragmentEmailValidBinding>(FragmentEmailValidBinding::bind, R.layout.fragment_email_valid) {

    private var authenticationCode : String = ""
    private val codeValidation = "^[A-Za-z0-9]{8}\$"
    private var isValidCode : Boolean = false

    private var timer = 600_000L
    private lateinit var countDownTimer: CountDownTimer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 카운트 다운 시작
        startCountDown(600_000L)

        // edittext에 입력한 값(authenticationCode) 받아오기
        getCode()

        // 뒤로 가기 클릭 시 이메일 입력으로 돌아감
        binding.emailValidBackIv.setOnClickListener {
            backFragment()
            countDownTimer.cancel()
        }

        // 다음 버튼 클릭 시 인증번호 validation 후 비밀번호 입력으로 넘어감
        binding.emailValidNextSelectBtn.setOnClickListener {
            checkValidCode()
        }

        // 메인 컨테이너 클릭 시 키보드 사라짐 && edittext에 포커스 사라짐
        binding.emailValidMainCl.setOnClickListener {
            hideKeyboard()
            binding.emailValidEt.clearFocus()
        }
    }

    private fun setTextTimer() {
        binding.emailValidTimerTv.text = String.format("%02d:%02d", (timer / 1000) / 60, (timer / 1000) % 60)
    }

    private fun startCountDown(startTime: Long) {
        countDownTimer = object : CountDownTimer(timer,1000){
            override fun onFinish() {
                countDownTimer.cancel()
                // TODO 인증 시간 지남 에러 메세지 출력 && 다시 재발급 && 카운터 재시작
            }

            override fun onTick(millisUntilFinished: Long) {
                timer = millisUntilFinished
                setTextTimer()
            }

        }.start()
    }

    private fun getCode() {
        binding.emailValidEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    binding.emailValidNextUnselectBtn.visibility = View.VISIBLE
                    binding.emailValidNextSelectBtn.visibility = View.GONE
                }
                else {
                    binding.emailValidNextUnselectBtn.visibility = View.GONE
                    binding.emailValidNextSelectBtn.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                authenticationCode = binding.emailValidEt.text.toString()
            }
        })
    }

    private fun checkValidCode() {
        val n = authenticationCode.trim() // 공백제거
        isValidCode = Pattern.matches(codeValidation, n)

        if(isValidCode) {
            binding.emailValidErrorTv.visibility = View.GONE
            countDownTimer.cancel() // 카운트다운 멈춤
            setTextTimer() // 타이머 텍스트 초기화
            moveFragment(R.id.action_emailValidFragment_to_pwdValidFragment)
        }
        else {
            binding.emailValidErrorTv.visibility = View.VISIBLE
            // TODO 인증 코드 validation 에러 메세지 출력하기
        }
    }
}
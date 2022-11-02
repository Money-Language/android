package com.cmccx.moge.presentation.view.signup

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmccx.moge.data.remote.api.CodeService
import com.cmccx.moge.data.remote.api.CodeView
import com.cmccx.moge.data.remote.model.Code
import com.cmccx.moge.data.remote.model.CodeResult
import com.cmccx.moge.databinding.FragmentEmailValidBinding
import java.util.regex.Pattern

class EmailValidFragment: Fragment(), CodeView {
    // thread로 인해서 view binding에 null 값이 들어감 (시간 차이인듯..?)
    // 근데 BaseFragment는 null 값이 들어갈 수가 없어서 에러 발생..
    // 따라서 EmailValidFragment만 binding을 따로 처리해줌
    lateinit var binding: FragmentEmailValidBinding

    private var authenticationCode : String = ""
    private val codeValidation = "^[A-Za-z0-9]{8}\$"
    private var isValidCode : Boolean = false

    private var timer = 600_000L
    private lateinit var countDownTimer: CountDownTimer

    // 이전 Fragment에서 넘겨받은 인자들
    private val args: EmailValidFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEmailValidBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 카운트 다운 시작
        startCountDown()

        // edittext에 입력한 값(authenticationCode) 받아오기
        getCode()

        // 뒤로 가기 클릭 시 이메일 입력으로 돌아감
        binding.emailValidBackIv.setOnClickListener {
            findNavController().navigateUp()
            countDownTimer.cancel()
        }

        // 다음 버튼 클릭 시 인증번호 validation 후 비밀번호 입력으로 넘어감
        binding.emailValidNextSelectBtn.setOnClickListener {
            checkValidCode()
            sendCode()
        }

        // 메인 컨테이너 클릭 시 키보드 사라짐 && edittext에 포커스 사라짐
        binding.emailValidMainCl.setOnClickListener {
            hideKeyboard()
            binding.emailValidEt.clearFocus()
        }
    }

    private fun startCountDown() {
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

    private fun setTextTimer() {
        binding.emailValidTimerTv.text = String.format("%02d:%02d", (timer / 1000) / 60, (timer / 1000) % 60)
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
            countDownTimer.cancel() // 카운트다운 멈춤
            setTextTimer() // 타이머 텍스트 초기화

            val action = EmailValidFragmentDirections.actionEmailValidFragmentToPwdValidFragment(args.flag, args.contract1, args.contract2, args.contract3, args.contract4, args.email)
            findNavController().navigate(action)
        }
    }

    private fun hideKeyboard() {
        if (activity != null && requireActivity().currentFocus != null) {
            val inputManager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun sendCode() {
        val codeService = CodeService(this)
        codeService.getCode(Code(args.email, authenticationCode))
    }

    override fun onGetCodeResultSuccess(result: CodeResult) {
        binding.emailValidErrorTv.visibility = View.GONE

        countDownTimer.cancel() // 카운트다운 멈춤
        setTextTimer() // 타이머 텍스트 초기화

        val action = EmailValidFragmentDirections.actionEmailValidFragmentToPwdValidFragment(args.flag, args.contract1, args.contract2, args.contract3, args.contract4, args.email)
        findNavController().navigate(action)
    }

    override fun onGetCodeResultFailure(message: String) {
        binding.emailValidErrorTv.visibility = View.VISIBLE
        binding.emailValidErrorTv.text = message
        Log.d("Check Code/API", message)
    }
}
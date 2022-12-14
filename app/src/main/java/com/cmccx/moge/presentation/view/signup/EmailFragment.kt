package com.cmccx.moge.presentation.view.signup

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.data.remote.api.EmailService
import com.cmccx.moge.data.remote.api.EmailValidationService
import com.cmccx.moge.data.remote.api.EmailValidationView
import com.cmccx.moge.data.remote.api.EmailView
import com.cmccx.moge.databinding.FragmentEmailBinding
import java.util.regex.Pattern

class EmailFragment : BaseFragment<FragmentEmailBinding>(FragmentEmailBinding::bind, R.layout.fragment_email), EmailView, EmailValidationView {

    private var email: String = ""
    private val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    private var isValidEmail: Boolean = false

    // 이전 Fragment에서 넘겨받은 인자들
    private val args: EmailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // edittext에 입력한 값(email) 받아오기
        getEmail()

        // 뒤로 가기 클릭 시 이용약관으로 돌아감
        binding.emailBackIv.setOnClickListener {
            backFragment()
        }

        // 이메일 인증 버튼 클릭 시 validation 검증 후 이메일 인증하기로 넘어감
        binding.emailNextSelectBtn.setOnClickListener {
            checkValidEmail()
            EmailValidationService(this).getEmailValidation(email)
        }

        // 메인 컨테이너 클릭 시 키보드 사라짐 && edittext에 포커스 사라짐
        binding.emailMainCl.setOnClickListener {
            hideKeyboard()
            binding.emailEt.clearFocus()
        }
    }

    private fun getEmail() {
        binding.emailEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    binding.emailNextUnselectBtn.visibility = View.VISIBLE
                    binding.emailNextSelectBtn.visibility = View.GONE
                }
                else {
                    binding.emailNextUnselectBtn.visibility = View.GONE
                    binding.emailNextSelectBtn.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                email = binding.emailEt.text.toString()
            }
        })
    }

    // 이메일 검증 메소드
    private fun checkValidEmail() {
        val n = email.trim() // 공백제거
        isValidEmail = Pattern.matches(emailValidation, n)

        if(isValidEmail) binding.emailErrorTv.visibility = View.GONE
        else binding.emailErrorTv.visibility = View.VISIBLE
    }

    // 이메일 검증 API 성공 -> 서버에 이메일 보내기
    override fun onGetEmailValidationResultSuccess() {
        binding.emailErrorTv.visibility = View.GONE
        EmailService(this).getEmail(email)
    }

    // 이메일 검증 API 실패
    override fun onGetEmailValidationResultFailure(message: String) {
        val errorMsg = "* $message"
        binding.emailErrorTv.text = errorMsg
        binding.emailErrorTv.visibility = View.VISIBLE
    }

    // 서버에 이메일 보냄
    override fun onGetEmailResultSuccess() {
        // Safe Args - 입력 받은 정보들 다음 fragment로 넘기기
        val action = EmailFragmentDirections.actionEmailFragmentToEmailValidFragment(args.flag, args.contract1, args.contract2, args.contract3, args.contract4, email)
        findNavController().navigate(action)
    }

    override fun onGetEmailResultFailure(message: String) {
        Log.d(TAG, "이메일 전송 실패 - $message")
    }
}

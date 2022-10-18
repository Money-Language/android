package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentEmailBinding
import java.util.regex.Pattern

class EmailFragment : BaseFragment<FragmentEmailBinding>(FragmentEmailBinding::bind, R.layout.fragment_email) {

    private var email: String = ""
    private val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    private var isValidEmail: Boolean = false

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

    private fun checkValidEmail() {
        val n = email.trim() // 공백제거
        isValidEmail = Pattern.matches(emailValidation, n)

        if(isValidEmail) {
            binding.emailErrorTv.visibility = View.GONE
            moveFragment(R.id.action_emailFragment_to_emailValidFragment)
        }
        else {
            binding.emailErrorTv.visibility = View.VISIBLE
            // TODO 이메일 validation 에러 메세지 출력하기
        }
    }
}

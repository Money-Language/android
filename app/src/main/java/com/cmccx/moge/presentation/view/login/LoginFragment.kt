package com.cmccx.moge.presentation.view.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {

    private var email: String = ""
    private var pwd: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // edittext에 입력한 값(email, pwd) 받아오기
        getEmail()
        getPwd()

        // 회원가입 클릭 시 회원가입으로 넘어감
        binding.loginSignupEmailCl.setOnClickListener {
            moveFragment(R.id.action_loginFragment_to_tosFragment)
        }

        binding.loginMainCl.setOnClickListener {
            hideKeyboard()
            binding.loginEmailEt.clearFocus()
            binding.loginPasswordEt.clearFocus()
        }
    }


    private fun getEmail() {
        binding.loginEmailEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                email = binding.loginEmailEt.text.toString()
            }
        })
    }

    private fun getPwd() {
        binding.loginPasswordEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                pwd = binding.loginPasswordEt.text.toString()
            }
        })
    }
}
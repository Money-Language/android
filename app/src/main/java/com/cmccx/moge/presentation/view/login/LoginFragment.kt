package com.cmccx.moge.presentation.view.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.base.saveJwt
import com.cmccx.moge.data.remote.api.LoginService
import com.cmccx.moge.data.remote.api.LoginView
import com.cmccx.moge.data.remote.model.Login
import com.cmccx.moge.data.remote.model.UserResult
import com.cmccx.moge.databinding.FragmentLoginBinding
import com.cmccx.moge.presentation.view.MainActivity

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login), LoginView {

    private var email: String = ""
    private var pwd: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // edittext에 입력한 값(email, pwd) 받아오기
        getEmail()
        getPwd()

        // 회원가입 클릭 시 회원가입으로 넘어감
        binding.loginSignupEmailCl.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToTosFragment("1")
            findNavController().navigate(action)
        }

        binding.loginMainCl.setOnClickListener {
            hideKeyboard()
            binding.loginEmailEt.clearFocus()
            binding.loginPasswordEt.clearFocus()
        }

        binding.loginBtn.setOnClickListener {
            login()
        }

        // TODO 카카오 로그인
        binding.loginKakaoIv.setOnClickListener {
            kakaoLogin()
            val action = LoginFragmentDirections.actionLoginFragmentToNicknameFragment("2", "", "", "", "", "", "", "")
            findNavController().navigate(action)
        }

        // TODO 네이버 로그인
        binding.loginNaverIv.setOnClickListener {
            naverLogin()
            val action = LoginFragmentDirections.actionLoginFragmentToNicknameFragment("3", "", "", "", "", "", "", "")
            findNavController().navigate(action)
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

    private fun login() {
        val loginService = LoginService(this)
        loginService.login(Login(email, pwd))
    }

    override fun onGetLoginResultSuccess(result: UserResult) {
        saveJwt(this.requireContext(), result.jwt)
        startActivity(Intent(requireContext(), MainActivity::class.java))
        //moveFragment(R.id.action_loginFragment_to_mainActivity)
    }

    override fun onGetLoginResultFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d("signUp/API", message)
    }

    private fun kakaoLogin() {

    }

    private fun naverLogin() {

    }
}
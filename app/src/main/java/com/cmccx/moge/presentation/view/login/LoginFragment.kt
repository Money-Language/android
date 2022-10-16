package com.cmccx.moge.presentation.view.login

import android.os.Bundle
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 회원가입 클릭 시 회원가입으로 넘어감
        binding.loginSignupEmailCl.setOnClickListener {
            moveFragment(R.id.action_loginFragment_to_tosFragment)
        }
    }
}
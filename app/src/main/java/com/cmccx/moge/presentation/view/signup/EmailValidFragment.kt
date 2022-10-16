package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentEmailValidBinding

class EmailValidFragment : BaseFragment<FragmentEmailValidBinding>(FragmentEmailValidBinding::bind, R.layout.fragment_email_valid) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로 가기 클릭 시 이메일 입력으로 돌아감
        binding.emailValidBackIv.setOnClickListener {
            moveFragment(R.id.action_emailValidFragment_to_emailFragment)
        }

        // 다음 버튼 클릭 시 비밀번호 입력으로 넘어감
        binding.emailValidNextSelectBtn.setOnClickListener {
            moveFragment(R.id.action_emailValidFragment_to_pwdValidFragment)
        }

        // TODO 카운트다운 구현하기, 인증번호 입력 시 버튼 색 변경하기
    }
}
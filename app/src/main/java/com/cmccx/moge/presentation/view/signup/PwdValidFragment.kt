package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentPwdValidBinding

class PwdValidFragment : BaseFragment<FragmentPwdValidBinding>(FragmentPwdValidBinding::bind, R.layout.fragment_pwd_valid) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로 가기 클릭 시 이메일 인증으로 돌아감
        binding.pwdValidBackIv.setOnClickListener {
            moveFragment(R.id.action_pwdValidFragment_to_emailValidFragment)
        }

        // 다음 버튼 클릭 시 닉네임 입력로 넘어감
        binding.pwdValidNextSelectBtn.setOnClickListener {
            moveFragment(R.id.action_pwdValidFragment_to_nicknameFragment)
        }

        // TODO 비밀번호 validation, 버튼 색 변경하기
    }
}
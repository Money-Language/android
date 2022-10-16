package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentTosBinding

class TosFragment : BaseFragment<FragmentTosBinding>(FragmentTosBinding::bind, R.layout.fragment_tos) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(checkAgree()) binding.tosNextBtn.visibility = View.VISIBLE
        else binding.tosNextBtn.visibility = View.GONE

        // 뒤로 가기 클릭 시 로그인으로 돌아감
        binding.tosBackIv.setOnClickListener {
            moveFragment(R.id.action_tosFragment_to_loginFragment)
        }

        // 다음 버튼 클릭 시 이메일로 넘어감
        binding.tosNextBtn.setOnClickListener {
            moveFragment(R.id.action_tosFragment_to_emailFragment)
        }
    }

    // TODO 회원가입 체크박스
    private fun checkAgree() : Boolean {
        return true
    }
}
package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentEmailBinding

class EmailFragment : BaseFragment<FragmentEmailBinding>(FragmentEmailBinding::bind, R.layout.fragment_email) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뒤로 가기 클릭 시 이용약관으로 돌아감
        binding.emailBackIv.setOnClickListener {
            moveFragment(R.id.action_emailFragment_to_tosFragment)
        }

        // 이메일 인증 버튼 클릭 시 이메일 인증하기로 넘어감
        binding.emailNextSelectBtn.setOnClickListener {
            moveFragment(R.id.action_emailFragment_to_emailValidFragment)
        }

        // TODO 이메일 값 들어오면 버튼 색 변경하기, 이메일 validation
    }
}

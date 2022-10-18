package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentFinishSignupBinding

class FinishSignupFragment : BaseFragment<FragmentFinishSignupBinding>(FragmentFinishSignupBinding::bind, R.layout.fragment_finish_signup) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.finishSignupBackIv.setOnClickListener {
            backFragment()
        }

        binding.finishSignupNextBtn.setOnClickListener {
            moveFragment(R.id.action_finishSignupFragment_to_loginFragment)
            // TODO fragment stack 지우기..
        }
    }
}
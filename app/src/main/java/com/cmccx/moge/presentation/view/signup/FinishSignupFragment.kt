package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentFinishSignupBinding

class FinishSignupFragment : BaseFragment<FragmentFinishSignupBinding>(FragmentFinishSignupBinding::bind, R.layout.fragment_finish_signup) {
    // 이전 Fragment에서 넘겨받은 인자들
    private val args: FinishSignupFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.finishSignupNicknameTv.text = args.nickname

        binding.finishSignupBackIv.setOnClickListener {
            backFragment()
        }

        binding.finishSignupNextBtn.setOnClickListener {
            moveFragment(R.id.action_finishSignupFragment_to_loginFragment)
        }
    }
}
package com.cmccx.moge.presentation.view.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentFinishSignupBinding
import com.cmccx.moge.presentation.view.MainActivity

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
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }
}
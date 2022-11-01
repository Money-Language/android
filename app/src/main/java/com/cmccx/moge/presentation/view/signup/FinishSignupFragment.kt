package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.data.remote.api.SignupService
import com.cmccx.moge.data.remote.api.SignupView
import com.cmccx.moge.data.remote.model.Signup
import com.cmccx.moge.data.remote.model.UserResult
import com.cmccx.moge.databinding.FragmentFinishSignupBinding

class FinishSignupFragment : BaseFragment<FragmentFinishSignupBinding>(FragmentFinishSignupBinding::bind, R.layout.fragment_finish_signup), SignupView {
    private var jwt : String = ""
    private var userIdx : Int = -1

    // 이전 Fragment에서 넘겨받은 인자들
    private val args: FinishSignupFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.finishSignupNicknameTv.text = args.nickname

        binding.finishSignupBackIv.setOnClickListener {
            backFragment()
        }

        binding.finishSignupNextBtn.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val signUpService = SignupService(this)
        signUpService.signUp(Signup(args.email!!, args.password!!, args.rePassword!!, args.nickname, args.contract1!!, args.contract2!!, args.contract3!!, args.contract4!!))
    }

    override fun onGetSignUpResultSuccess(result: UserResult) {
        jwt = result.jwt
        userIdx = result.userIdx
        moveFragment(R.id.action_finishSignupFragment_to_loginFragment)
    }

    override fun onGetSignUpResultFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d("signUp/API", message)
    }
}
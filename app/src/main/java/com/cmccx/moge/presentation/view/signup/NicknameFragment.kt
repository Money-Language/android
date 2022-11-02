package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.base.saveJwt
import com.cmccx.moge.base.saveUserIdx
import com.cmccx.moge.data.remote.api.SignupService
import com.cmccx.moge.data.remote.api.SignupView
import com.cmccx.moge.data.remote.model.Signup
import com.cmccx.moge.data.remote.model.UserResult
import com.cmccx.moge.databinding.FragmentNicknameBinding
import java.util.regex.Pattern

// TODO flag 값에 따라서 넘겨줘야 하는 인자 값 다르게 설정하기 (1: 일반 로그인 / 2: 카카오 로그인 / 3: 네이버 로그인)
class NicknameFragment : BaseFragment<FragmentNicknameBinding>(FragmentNicknameBinding::bind, R.layout.fragment_nickname) , SignupView {

    private var nickname: String = ""
    private val nicknameValidation = "^[가-힣a-zA-Z0-9]{1,20}$"
    private var isValidNickname: Boolean = false

    // 이전 Fragment에서 넘겨받은 인자들
    private val args: NicknameFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // edittext에 입력한 값(nickname) 받아오기
        getNickname()

        when(args.flag) {
            "1" -> {
                binding.nicknamePagingTv.visibility = View.VISIBLE
                binding.nicknamePagingTotalTv.visibility = View.VISIBLE
            }
            "2", "3" -> {
                binding.nicknamePagingTv.visibility = View.GONE
                binding.nicknamePagingTotalTv.visibility = View.GONE
            }
        }

        // 뒤로 가기 클릭 시 비밀번호 설정으로 돌아감
        binding.nicknameBackIv.setOnClickListener {
            backFragment()
        }

        // 다음 버튼 클릭 시 validation 검증 후 키워드 선택으로 넘어감
        binding.nicknameNextSelectBtn.setOnClickListener {
            checkValidNickname()

            // TODO 닉네임 중복 여부 체크 API 추가

            signUp()
        }

        // 메인 컨테이너 클릭 시 키보드 사라짐 && edittext에 포커스 사라짐
        binding.nicknameMainCl.setOnClickListener {
            hideKeyboard()
            binding.nicknameEt.clearFocus()
        }
    }

    private fun getNickname() {
        binding.nicknameEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    binding.nicknameNextUnselectBtn.visibility = View.VISIBLE
                    binding.nicknameNextSelectBtn.visibility = View.GONE
                }
                else {
                    binding.nicknameNextUnselectBtn.visibility = View.GONE
                    binding.nicknameNextSelectBtn.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                nickname = binding.nicknameEt.text.toString()
            }
        })
    }

    private fun checkValidNickname() {
        val n = nickname.trim() // 공백제거
        isValidNickname = Pattern.matches(nicknameValidation, n)

        if(isValidNickname) {
            binding.nicknameErrorTv.visibility = View.GONE
        }
        else {
            binding.nicknameErrorTv.visibility = View.VISIBLE
            // TODO 닉네임 validation 에러 메세지 출력하기
        }
    }

    private fun signUp() {
        val signUpService = SignupService(this)
        signUpService.signUp(Signup(args.email!!, args.password!!, args.rePassword!!, nickname, args.contract1!!, args.contract2!!, args.contract3!!, args.contract4!!))
    }

    override fun onGetSignUpResultSuccess(result: UserResult) {
        // SP에 저장
        saveJwt(requireContext(), result.jwt)
        saveUserIdx(requireContext(), result.userIdx)

        Log.d("jwt", result.jwt)
        Log.d("userIdx", result.userIdx.toString())

        val action = NicknameFragmentDirections.actionNicknameFragmentToFavoriteCategoryFragment(args.flag, result.jwt, result.userIdx, nickname)
        findNavController().navigate(action)
    }

    override fun onGetSignUpResultFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d("signUp/API", message)
    }
}

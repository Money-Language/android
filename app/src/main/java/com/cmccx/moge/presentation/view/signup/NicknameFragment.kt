package com.cmccx.moge.presentation.view.signup

import android.content.ContentValues.TAG
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
import com.cmccx.moge.data.remote.api.*
import com.cmccx.moge.data.remote.model.*
import com.cmccx.moge.databinding.FragmentNicknameBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import java.util.regex.Pattern

// flag 값에 따른 로그인 (1: 일반 로그인 / 2: 카카오 로그인 / 3: 네이버 로그인)
class NicknameFragment : BaseFragment<FragmentNicknameBinding>(FragmentNicknameBinding::bind, R.layout.fragment_nickname), NicknameValidationView, SignupView, KakaoSignupView, NaverSignupView, KakaoLoginView, NaverLoginView {

    private var nickname: String = ""
    private val nicknameValidation = "^[가-힣a-zA-Z0-9]{1,8}$"
    private var isValidNickname: Boolean = false

    // 이전 Fragment에서 넘겨받은 인자들
    private val args: NicknameFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // edittext에 입력한 값(nickname) 받아오기
        getNickname()

        // flag에 따라서 페이징 textview 보임 여부 결정
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
            NicknameValidationService(this).getNicknameValidation(nickname)

            when(args.flag) {
                "1" -> signUp()
                "2" -> kakaoSignUp()
                "3" -> naverSignUp()
            }

        }

        // 메인 컨테이너 클릭 시 키보드 사라짐 && edittext에 포커스 사라짐
        binding.nicknameMainCl.setOnClickListener {
            hideKeyboard()
            binding.nicknameEt.clearFocus()
        }
    }

    // edittext에서 닉네임 받아오는 메소드
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

    // 닉네임 validation 체크
    private fun checkValidNickname() {
        val n = nickname.trim() // 공백제거
        isValidNickname = Pattern.matches(nicknameValidation, n)

        if(isValidNickname) binding.nicknameErrorTv.visibility = View.GONE
        else binding.nicknameErrorTv.visibility = View.VISIBLE
    }

    // 닉네임 검증 API 성공
    override fun onGetNicknameValidationResultSuccess() {
        binding.nicknameErrorTv.visibility = View.GONE
    }

    // 닉네임 검증 API 실패
    override fun onGetNicknameValidationResultFailure(message: String) {
        val errorMsg = "* $message"
        binding.nicknameErrorTv.text = errorMsg
        binding.nicknameErrorTv.visibility = View.VISIBLE
    }



    // 일반(이메일) 회원가입 API 연결
    private fun signUp() {
        val signUpService = SignupService(this)
        signUpService.signUp(Signup(args.email!!, args.password!!, args.rePassword!!, nickname, args.contract1!!, args.contract2!!, args.contract3!!, args.contract4!!))
    }

    // 일반(이메일) 회원가입 API 연결 성공
    override fun onGetSignUpResultSuccess(result: UserResult) {
        saveUserInfo(result.jwt, result.userIdx)
        moveCategory(result.jwt, result.userIdx)
    }

    // 일반(이메일) 회원가입 API 연결 실패
    override fun onGetSignUpResultFailure(message: String) {
        //Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d(TAG, "일반 회원가입/API 실패 - $message")
    }





    // 카카오 회원가입 API 연결
    private fun kakaoSignUp() {
        val kakaoSignupService = KakaoSignupService(this)
        kakaoSignupService.kakaoSignUp(SnsSignup(args.accessToken!!, nickname))
    }

    // 카카오 회원가입 API 연결 성공
    override fun onGetKakaoSignUpResultSuccess(result: SnsSignupResult) {
        kakaoLogin()
    }

    // 카카오 회원가입 API 연결 실패
    override fun onGetKakaoSignUpResultFailure(message: String) {
        //Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d(TAG, "카카오 회원가입/API 실패 - $message")
    }

    // 카카오 로그인 API 연결
    private fun kakaoLogin() {
        val kakaoLoginService = KakaoLoginService(this)
        kakaoLoginService.kakaoLogin(SnsLogin(args.accessToken!!))
    }

    // 카카오 로그인 API 연결 성공
    override fun onGetKakaoLoginResultSuccess(result: UserResult) {
        saveUserInfo(result.jwt, result.userIdx)
        moveCategory(result.jwt, result.userIdx)
    }

    // 카카오 로그인 API 연결 실패
    override fun onGetKakaoLoginResultFailure(message: String) {
        //Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d(TAG, "카카오 로그인/API 실패 - $message")
    }





    // 네이버 회원가입 및 로그인 API 연결
    private fun naverSignUp() {
        val naverSignupService = NaverSignupService(this)
        naverSignupService.naverSignUp(SnsSignup(args.accessToken!!, nickname))
    }

    // 네이버 회원가입 및 로그인 API 연결 성공
    override fun onGetNaverSignUpResultSuccess(result: SnsSignupResult) {
        naverLogin()
    }

    // 네이버 회원가입 및 로그인 API 연결 실패
    override fun onGetNaverSignUpResultFailure(message: String) {
        // Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d(TAG, "네이버 회원가입/API 실패 - $message")
    }

    // 네이버 로그인
    private fun naverLogin(){
        val naverLoginService = NaverLoginService(this)
        naverLoginService.naverLogin(SnsLogin(args.accessToken!!))
    }

    // 네이버 로그인 API 연결 성공
    override fun onGetNaverLoginResultSuccess(result: UserResult) {
        saveUserInfo(result.jwt, result.userIdx)
        moveCategory(result.jwt, result.userIdx)
    }

    // 네이버 로그인 API 연결 실패
    override fun onGetNaverLoginResultFailure(message: String) {
        // Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d(TAG, "네이버 로그인/API 실패 - $message")
    }




    // jwt, userIdx 저장 메소드
    private fun saveUserInfo(jwt: String, userIdx: Int) {
        // SP에 저장
        saveJwt(requireContext(), jwt)
        saveUserIdx(requireContext(), userIdx)

        Log.d(TAG, "회원가입 jwt - $jwt")
        Log.d(TAG, "회원가입 userIdx - $userIdx")
    }

    // 관심 키워드 프래그먼트로 넘어가는 메소드
    private fun moveCategory(jwt: String, userIdx: Int) {
        val action = NicknameFragmentDirections.actionNicknameFragmentToFavoriteCategoryFragment(args.flag, jwt, userIdx, nickname)
        findNavController().navigate(action)
    }
}

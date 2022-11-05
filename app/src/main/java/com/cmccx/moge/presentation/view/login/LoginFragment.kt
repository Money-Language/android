package com.cmccx.moge.presentation.view.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.base.saveJwt
import com.cmccx.moge.base.saveUserIdx
import com.cmccx.moge.data.remote.api.KakaoLoginView
import com.cmccx.moge.data.remote.api.LoginService
import com.cmccx.moge.data.remote.api.LoginView
import com.cmccx.moge.data.remote.api.NaverLoginView
import com.cmccx.moge.data.remote.model.Login
import com.cmccx.moge.data.remote.model.UserResult
import com.cmccx.moge.databinding.FragmentLoginBinding
import com.cmccx.moge.presentation.view.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login), LoginView {

    private var email: String = ""
    private var pwd: String = ""

    private var kakaoToken: String = ""
    private var naverToken: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // edittext에 입력한 값(email, pwd) 받아오기
        getEmail()
        getPwd()

        // 회원가입 클릭 시 회원가입으로 넘어감
        binding.loginSignupEmailCl.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToTosFragment("1")
            findNavController().navigate(action)
        }

        // 전체 constrainLayout 누르면 키보드 내려가고 edittext에 포커스 사라짐
        binding.loginMainCl.setOnClickListener {
            hideKeyboard()
            binding.loginEmailEt.clearFocus()
            binding.loginPasswordEt.clearFocus()
        }

        // 로그인 버튼 클릭 시 일반(이메일)로그인
        binding.loginBtn.setOnClickListener {
            login()
        }

        // 카카오 버튼 클릭 시 카카오 로그인
        binding.loginKakaoIv.setOnClickListener {
            kakaoLogin()
        }

        // 네이버 버튼 클릭 시 네이버 로그인
        binding.loginNaverIv.setOnClickListener {
            naverLogin()
        }
    }


    // edittext에서 이메일 받아오는 메소드
    private fun getEmail() {
        binding.loginEmailEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                email = binding.loginEmailEt.text.toString()
            }
        })
    }

    // edittext에서 패스워드 받아오는 메소드
    private fun getPwd() {
        binding.loginPasswordEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                pwd = binding.loginPasswordEt.text.toString()
            }
        })
    }

    // 일반(이메일) 로그인 API 연결
    private fun login() {
        val loginService = LoginService(this)
        loginService.login(Login(email, pwd))
    }

    // 일반(이메일) 로그인 API 연결 성공 -> 메인 액티비티로 넘어감
    // TODO 메인 액티비티에서 뒤로 가기 누르면 다시 로그인 화면으로 넘어감!!!!!!!!!! popUpToInclusive 써봤지만 안됌..
    override fun onGetLoginResultSuccess(result: UserResult) {
        saveUserInfo(result.jwt, result.userIdx)
        startActivity(Intent(requireContext(), MainActivity::class.java))
        //moveFragment(R.id.action_loginFragment_to_mainActivity)
    }

    // 일반(이메일) 로그인 API 연결 실패
    override fun onGetLoginResultFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d("Login/API", message)
    }





    // 카카오 로그인 -> 로그인 성공 시 accessToken 받아옴
    private fun kakaoLogin() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d(TAG, "카카오계정으로 로그인 실패 : ${error}")
            } else if (token != null) {
                kakaoToken = token.accessToken
                moveNicknameFragment("2",kakaoToken)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) {
                    Log.d(TAG, "카카오톡으로 로그인 실패 : ${error}")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
                } else if (token != null) {
                    Log.d(TAG, "카카오톡으로 로그인 성공")
                    kakaoToken = token.accessToken
                    moveNicknameFragment("2",kakaoToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
        }
    }





    // 네이버 로그인
    private fun naverLogin(){
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                naverToken = NaverIdLoginSDK.getAccessToken().toString()
                Log.d(TAG, "네이버 로그인 성공 : $naverToken")
                moveNicknameFragment("3", naverToken)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(TAG, "네이버 로그인 실패 : $message")
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        NaverIdLoginSDK.authenticate(requireContext(), oauthLoginCallback)
    }




    // jwt, userIdx 저장 메소드
    private fun saveUserInfo(jwt: String, userIdx: Int) {
        // SP에 저장
        saveJwt(requireContext(), jwt)
        saveUserIdx(requireContext(), userIdx)

        Log.d(TAG, "회원가입 jwt = $jwt")
        Log.d(TAG, "회원가입 userIdx = $userIdx")
    }

    // 카카오 or 네이버 로그인을 구분하는 인자 - flag (1 = 카카오, 2 = 네이버)
    private fun moveNicknameFragment(flag: String, accessToken: String) {
        // 받아온 정보 확인 log
        Log.d(TAG,"토큰 - $accessToken")

        // 닉네임 프래그먼트로 args(flag, email, accessToken) 전달
        val action = LoginFragmentDirections.actionLoginFragmentToNicknameFragment(flag, "", "", "", "","", "", "", accessToken)
        findNavController().navigate(action)
    }
}
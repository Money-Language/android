package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentPwdValidBinding
import java.util.regex.Pattern

class PwdValidFragment : BaseFragment<FragmentPwdValidBinding>(FragmentPwdValidBinding::bind, R.layout.fragment_pwd_valid) {

    private var pwd: String = ""
    private var checkPwd : String = ""
    private val pwdValidation = "^[A-Za-z0-9!@#\$%^&*]{5,20}\$"
    private var isValidPwd: Boolean = false
    private var isValidCheckPwd : Boolean = false
    private var isSame : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // edittext에 입력한 값(pwd, checkPwd) 받아오기
        getPwd()
        getCheckPwd()

        // 뒤로 가기 클릭 시 이메일 인증으로 돌아감
        binding.pwdValidBackIv.setOnClickListener {
            backFragment()
        }

        // 다음 버튼 클릭 시 비밀번호 validation 후 닉네임 입력로 넘어감
        binding.pwdValidNextSelectBtn.setOnClickListener {
            checkValidPwd()
        }

        // 메인 컨테이너 클릭 시 키보드 사라짐 && edittext에 포커스 사라짐
        binding.pwdValidMainCl.setOnClickListener {
            hideKeyboard()
            binding.pwdEt.clearFocus()
            binding.pwdValidEt.clearFocus()
        }
    }

    private fun getPwd() {
        binding.pwdEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    binding.pwdValidNextUnselectBtn.visibility = View.VISIBLE
                    binding.pwdValidNextSelectBtn.visibility = View.GONE
                }
                else {
                    binding.pwdValidNextUnselectBtn.visibility = View.GONE
                    binding.pwdValidNextSelectBtn.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                pwd = binding.pwdEt.text.toString()
            }
        })
    }

    private fun getCheckPwd() {
        binding.pwdValidEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    binding.pwdValidNextUnselectBtn.visibility = View.VISIBLE
                    binding.pwdValidNextSelectBtn.visibility = View.GONE
                }
                else {
                    binding.pwdValidNextUnselectBtn.visibility = View.GONE
                    binding.pwdValidNextSelectBtn.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                 checkPwd = binding.pwdValidEt.text.toString()
            }
        })
    }

    private fun checkValidPwd() {
        val n1 = pwd.trim()
        val n2 = checkPwd.trim()

        isValidPwd = Pattern.matches(pwdValidation, n1)
        isValidCheckPwd = Pattern.matches(pwdValidation, n2)
        isSame = pwd == checkPwd

        if(isValidPwd && isValidCheckPwd && isSame) {
            binding.pwdErrorTv.visibility = View.GONE
            binding.pwdValidErrorTv.visibility = View.GONE
            moveFragment(R.id.action_pwdValidFragment_to_nicknameFragment)
        }
        else {
            if(!isValidPwd) {
                binding.pwdErrorTv.visibility = View.VISIBLE
                binding.pwdValidErrorTv.visibility = View.GONE
                // TODO 비밀번호 validation 에러 메세지 출력
            }
            else {
                binding.pwdErrorTv.visibility = View.GONE
                binding.pwdValidErrorTv.visibility = View.VISIBLE
                // TODO 비밀번호 확인 validation 에러 메세지 출력
            }
        }
    }
}
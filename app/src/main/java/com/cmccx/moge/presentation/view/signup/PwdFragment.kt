package com.cmccx.moge.presentation.view.signup

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.data.remote.api.NicknameValidationService
import com.cmccx.moge.data.remote.api.PasswordValidationService
import com.cmccx.moge.data.remote.api.PasswordValidationView
import com.cmccx.moge.data.remote.model.Password
import com.cmccx.moge.databinding.FragmentPwdBinding
import java.util.regex.Pattern

class PwdFragment : BaseFragment<FragmentPwdBinding>(FragmentPwdBinding::bind, R.layout.fragment_pwd), PasswordValidationView {

    private var pwd: String = ""
    private var checkPwd : String = ""
    private val pwdValidation = "^[A-Za-z0-9!@#\$%^&*]{5,20}\$"
    private var isValidPwd: Boolean = false
    private var isValidCheckPwd : Boolean = false
    private var isSame : Boolean = false

    // 이전 Fragment에서 넘겨받은 인자들
    private val args: PwdFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // edittext에 입력한 값(pwd, checkPwd) 받아오기
        getPwd()
        getCheckPwd()

        // 뒤로 가기 클릭 시 이메일 인증으로 돌아감
        binding.pwdBackIv.setOnClickListener {
            backFragment()
        }

        // 다음 버튼 클릭 시 비밀번호 validation 후 닉네임 입력로 넘어감
        binding.pwdNextSelectBtn.setOnClickListener {
            checkValidPwd()
            PasswordValidationService(this).getPasswordValidation(Password(pwd, checkPwd))
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
                    binding.pwdNextUnselectBtn.visibility = View.VISIBLE
                    binding.pwdNextSelectBtn.visibility = View.GONE
                }
                else {
                    binding.pwdNextUnselectBtn.visibility = View.GONE
                    binding.pwdNextSelectBtn.visibility = View.VISIBLE
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
                    binding.pwdNextUnselectBtn.visibility = View.VISIBLE
                    binding.pwdNextSelectBtn.visibility = View.GONE
                }
                else {
                    binding.pwdNextUnselectBtn.visibility = View.GONE
                    binding.pwdNextSelectBtn.visibility = View.VISIBLE
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
        isSame = (pwd == checkPwd)

        /*if (!isValidCheckPwd || !isValidPwd)  {
            binding.pwdValidErrorTv.text = getString(R.string.password_formatting)
            binding.pwdValidErrorTv.visibility = View.VISIBLE
        }*/


        /*if(isValidPwd && isValidCheckPwd && isSame) {
            binding.pwdErrorTv.visibility = View.GONE
            binding.pwdValidErrorTv.visibility = View.GONE
        }
        else {
            if(isValidPwd) {
                binding.pwdErrorTv.visibility = View.GONE
                binding.pwdValidErrorTv.visibility = View.GONE
            }
            else {
                binding.pwdErrorTv.visibility = View.VISIBLE
                binding.pwdValidErrorTv.visibility = View.GONE
            }

            if(isValidCheckPwd && isSame) {
                binding.pwdErrorTv.visibility = View.GONE
                binding.pwdValidErrorTv.visibility = View.GONE
            }
            else {
                binding.pwdErrorTv.visibility = View.GONE


            }
        }*/
    }

    // 비밀번호 검증 API 성공
    override fun onGetPasswordValidationResultSuccess() {
        binding.pwdErrorTv.visibility = View.GONE
        binding.pwdValidErrorTv.visibility = View.GONE

        val action = PwdFragmentDirections.actionPwdFragmentToNicknameFragment(args.flag, args.contract1, args.contract2, args.contract3, args.contract4, args.email, pwd, checkPwd, "")
        findNavController().navigate(action)
    }

    @SuppressLint("SetTextI18n")
    override fun onGetPasswordValidationResultFailure(code: Int, message: String) {
        if(code == 2017) {
            binding.pwdErrorTv.text = "* $message"
            binding.pwdErrorTv.visibility = View.VISIBLE
            binding.pwdValidErrorTv.visibility = View.GONE
        } else if (code == 2018) {
            binding.pwdValidErrorTv.text = "* $message"
            binding.pwdErrorTv.visibility = View.GONE
            binding.pwdValidErrorTv.visibility = View.VISIBLE
        }
    }
}
package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentNicknameBinding
import java.util.regex.Pattern

class NicknameFragment : BaseFragment<FragmentNicknameBinding>(FragmentNicknameBinding::bind, R.layout.fragment_nickname) {

    private var nickname: String = ""
    private val nicknameValidation = "^[가-힣a-zA-Z0-9]{1,20}$"
    private var isValidNickname: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // edittext에 입력한 값(nickname) 받아오기
        getNickname()

        // 뒤로 가기 클릭 시 비밀번호 설정으로 돌아감
        binding.nicknameBackIv.setOnClickListener {
            backFragment()
        }

        // 다음 버튼 클릭 시 validation 검증 후 키워드 선택으로 넘어감
        binding.nicknameNextSelectBtn.setOnClickListener {
            checkValidNickname()
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
            moveFragment(R.id.action_nicknameFragment_to_favoriteCategoryFragment)
        }
        else {
            binding.nicknameErrorTv.visibility = View.VISIBLE
            // TODO 닉네임 validation 에러 메세지 출력하기
        }
    }
}

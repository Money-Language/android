package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentTosBinding

class TosFragment : BaseFragment<FragmentTosBinding>(FragmentTosBinding::bind, R.layout.fragment_tos) {

    private var isOtherAgree : Boolean = false
    private var isAgeAgree : Boolean = false
    private var isTosAgree : Boolean = false
    private var isPiAgree : Boolean = false
    private var isThirdPartyAgree : Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkAgree()

        // 뒤로 가기 클릭 시 로그인으로 돌아감
        binding.tosBackIv.setOnClickListener {
            moveFragment(R.id.action_tosFragment_to_loginFragment)
        }

        // 다음 버튼 클릭 시 이메일로 넘어감
        binding.tosNextBtn.setOnClickListener {
            moveFragment(R.id.action_tosFragment_to_emailFragment)
        }
    }

    private fun checkAgree() {
        binding.tosAgreeAllCb.setOnCheckedChangeListener { _, isChecked ->
            if (!isOtherAgree) {
                binding.tosAgreeAgeCb.isChecked = isChecked
                binding.tosAgreeTosCb.isChecked = isChecked
                binding.tosAgreePiCb.isChecked = isChecked
                binding.tosAgreeThirdPartyCb.isChecked = isChecked

                isAgeAgree = isChecked
                isTosAgree = isChecked
                isPiAgree = isChecked
                isThirdPartyAgree = isChecked
            }
            controlNextButton()
        }

        binding.tosAgreeAgeCb.setOnCheckedChangeListener { _, isChecked ->
            isAgeAgree = isChecked
            checkAllAgree()
            controlNextButton()
        }

        binding.tosAgreeTosCb.setOnCheckedChangeListener { _, isChecked ->
            isTosAgree = isChecked
            checkAllAgree()
            controlNextButton()
        }

        binding.tosAgreePiCb.setOnCheckedChangeListener { _, isChecked ->
            isPiAgree = isChecked
            checkAllAgree()
            controlNextButton()
        }

        binding.tosAgreeThirdPartyCb.setOnCheckedChangeListener { _, isChecked ->
            isThirdPartyAgree = isChecked
            checkAllAgree()
            controlNextButton()
        }

    }

    private fun checkAllAgree() {
        if (isAgeAgree && isTosAgree && isPiAgree && isThirdPartyAgree) binding.tosAgreeAllCb.isChecked = true
        else if (!(isAgeAgree || isTosAgree || isPiAgree || isThirdPartyAgree)) binding.tosAgreeAllCb.isChecked = false
        else {
            isOtherAgree = true
            binding.tosAgreeAllCb.isChecked = false
            isOtherAgree = false
        }
    }

    private fun controlNextButton() {
        if (isAgeAgree && isTosAgree && isPiAgree) binding.tosNextBtn.visibility = View.VISIBLE
        else binding.tosNextBtn.visibility = View.GONE
    }
}
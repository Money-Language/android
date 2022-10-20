package com.cmccx.moge.presentation.view.chatting

import android.content.Context
import android.os.Bundle
import android.view.View
import com.cmccx.moge.R
import com.cmccx.moge.base.ApplicationClass
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentChattingBinding
import com.cmccx.moge.presentation.view.MainOwner

class ChattingFragment: BaseFragment<FragmentChattingBinding>(FragmentChattingBinding::bind, R.layout.fragment_chatting) {

    private lateinit var owner: MainOwner

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainOwner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        owner.setBottomNavVisible(false)
        owner.setFloatingBtnVisible(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        owner.setActionBarVisible(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        owner.setBottomNavVisible(true)
        owner.setFloatingBtnVisible(true)
    }

}
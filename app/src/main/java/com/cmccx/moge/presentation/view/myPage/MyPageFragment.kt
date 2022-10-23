package com.cmccx.moge.presentation.view.myPage

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentMyPageBinding
import com.cmccx.moge.presentation.view.MainOwner
import com.cmccx.moge.presentation.view.myPage.myQuiz.MyQuizFragment
import com.google.android.material.tabs.TabLayout

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {

    private lateinit var owner: MainOwner

    private val myQuizFragment = MyQuizFragment()
    private val myFeedFragment = MyFeedFragment()
    private val myFavFragment = MyFavFragment()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainOwner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        owner.setFloatingBtnVisible(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction().replace(binding.mypageTabFrameFl.id, myQuizFragment).commit()

        tabListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        owner.setFloatingBtnVisible(true)
    }

    private fun tabListener() {
        binding.mypageContentsTabTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab!!.position
                var selectedFragment: Fragment? = null

                when (position) {
                    0 -> {
                        selectedFragment = myQuizFragment
                    }
                    1 -> {
                        selectedFragment = myFeedFragment
                    }
                    2 -> {
                        selectedFragment = myFavFragment
                    }
                }

                if (selectedFragment != null) {
                    childFragmentManager.beginTransaction().replace(binding.mypageTabFrameFl.id, selectedFragment).commit()
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

}
package com.cmccx.moge.presentation.view.myPage

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.base.getJwt
import com.cmccx.moge.base.getUserIdx
import com.cmccx.moge.data.remote.api.ProfileService
import com.cmccx.moge.data.remote.api.ProfileView
import com.cmccx.moge.data.remote.model.Profile
import com.cmccx.moge.databinding.FragmentMyPageBinding
import com.cmccx.moge.presentation.view.MainOwner
import com.cmccx.moge.presentation.view.myPage.myFav.MyFavFragment
import com.cmccx.moge.presentation.view.myPage.myFeed.MyFeedFragment
import com.cmccx.moge.presentation.view.myPage.myQuiz.MyQuizFragment
import com.cmccx.moge.presentation.viewmodel.PointViewModel
import com.google.android.material.tabs.TabLayout

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page), ProfileView {

    private lateinit var owner: MainOwner
    private val pointViewModel: PointViewModel by activityViewModels()

    private val myQuizFragment = MyQuizFragment()
    private val myFeedFragment = MyFeedFragment()
    private val myFavFragment = MyFavFragment()

    private var jwt : String = ""
    private var userIdx: Int = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jwt = getJwt(requireContext())
        userIdx = getUserIdx(requireContext())

        childFragmentManager.beginTransaction().replace(binding.mypageTabFrameFl.id, myQuizFragment).commit()

        tabListener()
        initView()

        binding.mypageHeaderSettingIv.setOnClickListener {

        }
    }

    private fun tabListener() {
        binding.mypageContentsTabTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab!!.position
                var selectedFragment: Fragment? = null

                when (position) {
                    0 -> { selectedFragment = myQuizFragment }
                    1 -> { selectedFragment = myFeedFragment }
                    2 -> { selectedFragment = myFavFragment }
                }

                if (selectedFragment != null) {
                    childFragmentManager.beginTransaction().replace(binding.mypageTabFrameFl.id, selectedFragment).commit()
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initView() {
        // 플로팅 버튼 사라지기
        owner.setFloatingBtnVisible(false)

        // 상단 포인트
        binding.point = pointViewModel
        pointViewModel.getPoint(jwt, userIdx)

        // 사용자 프로필 조회
        getUser()
    }

    // 사용자 프로필 조회
    private fun getUser() {
        val profileService = ProfileService(this)
        profileService.getProfile(jwt, userIdx)
    }

    // 사용자 프로필 조회 성공
    override fun onGetProfileResultSuccess(result: Profile) {
        if (result.profileImage.isNullOrEmpty()) {
            binding.mypageProfileRoundProfileRiv.setImageResource(R.drawable.icon_profile)
        } else {
            Glide.with(requireContext()).load(result.profileImage).into(binding.mypageProfileRoundProfileRiv)
        }
        binding.mypageProfileNameTv.text = result.nickname
        binding.mypageProfileFollowerContentTv.text = result.followerCount.toString()
        binding.mypageProfileFollowingContentTv.text = result.followingCount.toString()
    }

    // 사용자 프로필 조회 실패
    override fun onGetProfileResultFailure(message: String) {
        Log.d(TAG,"프로필 조회 실패 - $message")
    }

}
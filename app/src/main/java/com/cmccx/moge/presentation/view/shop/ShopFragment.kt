package com.cmccx.moge.presentation.view.shop

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.base.getJwt
import com.cmccx.moge.base.getUserIdx
import com.cmccx.moge.databinding.FragmentShopBinding
import com.cmccx.moge.presentation.view.MainOwner
import com.cmccx.moge.presentation.view.shop.best.ShopBestFragment
import com.cmccx.moge.presentation.view.shop.cafe.ShopCafeFragment
import com.cmccx.moge.presentation.view.shop.culture.ShopCultureFragment
import com.cmccx.moge.presentation.view.shop.food.ShopFoodFragment
import com.cmccx.moge.presentation.view.shop.goods.ShopGoodsFragment
import com.cmccx.moge.presentation.viewmodel.PointViewModel
import com.google.android.material.tabs.TabLayout

// TODO 다이얼로그 띄우기
class ShopFragment : BaseFragment<FragmentShopBinding>(FragmentShopBinding::bind, R.layout.fragment_shop) {
    private lateinit var owner: MainOwner
    private val pointViewModel: PointViewModel by activityViewModels()

    private val shopBestFragment = ShopBestFragment()
    private val shopCafeFragment = ShopCafeFragment()
    private val shopFoodFragment = ShopFoodFragment()
    private val shopCultureFragment = ShopCultureFragment()
    private val shopGoodsFragment = ShopGoodsFragment()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction().replace(binding.shopTabFrameFl.id, shopBestFragment).commit()

        tabListener()
        initView()
    }

    private fun tabListener() {
        binding.shopContentsTabTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab!!.position
                var selectedFragment: Fragment? = null

                when (position) {
                    0 -> { selectedFragment = shopBestFragment }
                    1 -> { selectedFragment = shopCafeFragment }
                    2 -> { selectedFragment = shopFoodFragment }
                    3 -> { selectedFragment = shopCultureFragment }
                    4 -> { selectedFragment = shopGoodsFragment }
                }

                if (selectedFragment != null) {
                    childFragmentManager.beginTransaction().replace(binding.shopTabFrameFl.id, selectedFragment).commit()
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initView(){
        owner.setFloatingBtnVisible(false)

        // 상단 포인트
        binding.point = pointViewModel
        pointViewModel.getPoint(getJwt(this.requireContext()), getUserIdx(this.requireContext()))
    }
}
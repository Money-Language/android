package com.cmccx.moge.presentation.view.shop.food

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentShopFoodBinding

class ShopFoodFragment : BaseFragment<FragmentShopFoodBinding>(FragmentShopFoodBinding::bind, R.layout.fragment_shop_food) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.itemShopFoodRv.adapter = ShopFoodAdapter(requireContext())
        binding.itemShopFoodRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }
}
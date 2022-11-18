package com.cmccx.moge.presentation.view.shop.culture

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentShopCultureBinding

class ShopCultureFragment : BaseFragment<FragmentShopCultureBinding>(FragmentShopCultureBinding::bind, R.layout.fragment_shop_culture) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.itemShopCultureRv.adapter = ShopCultureAdapter(requireContext())
        binding.itemShopCultureRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }
}
package com.cmccx.moge.presentation.view.shop.cafe

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentShopCafeBinding

class ShopCafeFragment : BaseFragment<FragmentShopCafeBinding>(FragmentShopCafeBinding::bind, R.layout.fragment_shop_cafe) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.itemShopCafeRv.adapter = ShopCafeAdapter(requireContext())
        binding.itemShopCafeRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }

}
package com.cmccx.moge.presentation.view.shop.goods

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentShopGoodsBinding

class ShopGoodsFragment : BaseFragment<FragmentShopGoodsBinding>(FragmentShopGoodsBinding::bind, R.layout.fragment_shop_goods) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.itemShopGoodsRv.adapter = ShopGoodsAdapter(requireContext())
        binding.itemShopGoodsRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }
}
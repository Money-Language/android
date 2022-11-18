package com.cmccx.moge.presentation.view.shop.best

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentShopBestBinding

class ShopBestFragment : BaseFragment<FragmentShopBestBinding>(FragmentShopBestBinding::bind, R.layout.fragment_shop_best) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.itemShopBestRv.adapter = ShopBestAdapter(requireContext())
        binding.itemShopBestRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }

}
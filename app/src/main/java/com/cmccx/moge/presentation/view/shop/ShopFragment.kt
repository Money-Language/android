package com.cmccx.moge.presentation.view.shop

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentShopBinding

class ShopFragment : BaseFragment<FragmentShopBinding>(FragmentShopBinding::bind, R.layout.fragment_shop) {
    private val cateList = arrayListOf<String>()
    private val goodsList = arrayListOf<String>()

    init {
        cateList.add("dummy1")
        cateList.add("dummy2")
        cateList.add("dummy3")
        cateList.add("dummy4")
        cateList.add("dummy5")
        cateList.add("dummy6")
        cateList.add("dummy7")

        goodsList.add("CU1")
        goodsList.add("CU2")
        goodsList.add("CU3")
        goodsList.add("CU4")
        goodsList.add("CU5")
        goodsList.add("CU6")
        goodsList.add("CU7")
        goodsList.add("CU8")
        goodsList.add("CU9")
        goodsList.add("CU10")

    }

    override fun onStart() {
        super.onStart()
        setAdapter()
    }

    private fun setAdapter() {
        // 카테 헤더
        binding.itemShopCateHeaderRv.adapter = ShopCateAdapter(this.requireContext(), cateList)
        binding.itemShopCateHeaderRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 아이템 보드
        binding.itemShopGoodsBoardsRv.adapter = ShopGoodsAdapter(goodsList)
        binding.itemShopGoodsBoardsRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
    }
}
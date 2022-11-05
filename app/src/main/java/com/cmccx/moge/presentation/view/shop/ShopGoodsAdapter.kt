package com.cmccx.moge.presentation.view.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.databinding.ItemShopGoodsBinding

class ShopGoodsAdapter(private val shopGoodsList: ArrayList<String>) : RecyclerView.Adapter<ShopGoodsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopGoodsAdapter.ViewHolder {
        val view = ItemShopGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curItem = shopGoodsList[position]

        with (holder) {
            brand.text = curItem
        }
    }

    override fun getItemCount(): Int {
        return shopGoodsList.size
    }

    inner class ViewHolder(binding: ItemShopGoodsBinding) : RecyclerView.ViewHolder(binding.root) {
        val brand = binding.itemShopGoodsBrandTv
    }
}
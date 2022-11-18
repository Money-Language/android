package com.cmccx.moge.presentation.view.shop.goods

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.Goods
import com.cmccx.moge.databinding.ItemShopGoodsBinding

class ShopGoodsAdapter(private val context: Context) : RecyclerView.Adapter<ShopGoodsAdapter.ViewHolder>() {
    private val shopGoodsList = ArrayList<Goods>()

    init {
        shopGoodsList.add(Goods("9,345", R.drawable.item_goods_ex_1, "JAJU", "촉촉한 약산성 고체 샴푸 바"))
        shopGoodsList.add(Goods("58,500", R.drawable.item_goods_ex_3, "스타벅스", "SS 그린 처비 핸들 텀블러"))
        shopGoodsList.add(Goods("10,350", R.drawable.item_goods_ex_5, "BOHO", "대나무키친타올 130매 6롤"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopGoodsAdapter.ViewHolder {
        val binding : ItemShopGoodsBinding = ItemShopGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopGoodsAdapter.ViewHolder, position: Int) {
        holder.bind(shopGoodsList[position])
    }

    override fun getItemCount(): Int {
        return shopGoodsList.size
    }

    inner class ViewHolder(val binding: ItemShopGoodsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(shopGoodsList: Goods) {
            binding.itemShopGoodsPointTv.text = shopGoodsList.point
            binding.itemShopGoodsBrandTv.text= shopGoodsList.brand
            binding.itemShopGoodsNameTv.text = shopGoodsList.name
            Glide.with(context).load(shopGoodsList.img).into(binding.itemShopGoodsIv)
        }
    }
}
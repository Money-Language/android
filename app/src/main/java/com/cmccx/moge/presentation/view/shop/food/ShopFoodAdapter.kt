package com.cmccx.moge.presentation.view.shop.food

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.Goods
import com.cmccx.moge.databinding.ItemShopGoodsBinding

class ShopFoodAdapter(private val context: Context) : RecyclerView.Adapter<ShopFoodAdapter.ViewHolder>() {
    private val shopGoodsList = ArrayList<Goods>()

    init {
        shopGoodsList.add(Goods("15,000", R.drawable.item_goods_ex_11, "신선설농탕", "설농탕"))
        shopGoodsList.add(Goods("16,500", R.drawable.item_goods_ex_12, "신선설농탕", "순사골국"))
        shopGoodsList.add(Goods("31,500", R.drawable.item_goods_ex_13, "신선설농탕", "도가니탕"))
        shopGoodsList.add(Goods("9,000", R.drawable.item_goods_ex_14, "신선설농탕", "신선왕만두"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopFoodAdapter.ViewHolder {
        val binding : ItemShopGoodsBinding = ItemShopGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopFoodAdapter.ViewHolder, position: Int) {
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
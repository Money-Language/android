package com.cmccx.moge.presentation.view.shop.culture

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.Goods
import com.cmccx.moge.databinding.ItemShopGoodsBinding

class ShopCultureAdapter(private val context: Context) : RecyclerView.Adapter<ShopCultureAdapter.ViewHolder>() {
    private val shopGoodsList = ArrayList<Goods>()

    init {
        shopGoodsList.add(Goods("93,000", R.drawable.item_goods_ex_15, "롯데월드 어드벤처", "롯데월드 어드벤처 티켓"))
        shopGoodsList.add(Goods("21,000", R.drawable.item_goods_ex_2, "롯데시네마", "영화관람권 1장"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopCultureAdapter.ViewHolder {
        val binding : ItemShopGoodsBinding = ItemShopGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopCultureAdapter.ViewHolder, position: Int) {
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
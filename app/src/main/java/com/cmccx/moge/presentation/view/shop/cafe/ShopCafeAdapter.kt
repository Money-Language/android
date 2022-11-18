package com.cmccx.moge.presentation.view.shop.cafe

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmccx.moge.R
import com.cmccx.moge.data.remote.model.Goods
import com.cmccx.moge.databinding.ItemShopGoodsBinding

class ShopCafeAdapter(private val context: Context) : RecyclerView.Adapter<ShopCafeAdapter.ViewHolder>() {
    private val shopGoodsList = ArrayList<Goods>()

    init {
        shopGoodsList.add(Goods("9,450", R.drawable.item_goods_ex_7, "스타벅스", "그린티 크림 프라푸치노 T"))
        shopGoodsList.add(Goods("9,000", R.drawable.item_goods_ex_8, "스타벅스", "돌체 콜드 브루 T"))
        shopGoodsList.add(Goods("9,450", R.drawable.item_goods_ex_9, "스타벅스", "망고 바나나 블렌디드 G"))
        shopGoodsList.add(Goods("6,750", R.drawable.item_goods_ex_4, "스타벅스", "아이스 아메리카노 T"))
        shopGoodsList.add(Goods("7,500", R.drawable.item_goods_ex_10, "스타벅스", "아이스 카페라떼 T"))
        shopGoodsList.add(Goods("9,450", R.drawable.item_goods_ex_6, "스타벅스", "자바 칩 프라푸치노 T"))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopCafeAdapter.ViewHolder {
        val binding : ItemShopGoodsBinding = ItemShopGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopCafeAdapter.ViewHolder, position: Int) {
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
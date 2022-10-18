package com.cmccx.moge.presentation.view.signup

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.databinding.ItemFavoriteCategoryBinding

class FavoriteCategorySelectAdapter(private var categoryList: ArrayList<String>) : RecyclerView.Adapter<FavoriteCategorySelectAdapter.ViewHolder>() {

    private var isClicked : Boolean = false

    /*// 클릭 인터페이스 정의
    interface CategoryClickListener{
        fun onItemClick(categoryList: String, isClicked: Boolean)
    }

    // 리스너 객체를 전달받는 함수랑 리스너 객체를 저장할 변수
    private lateinit var categoryClickListener : CategoryClickListener

    fun setCategoryClickListener(categoryClickListener: CategoryClickListener){
        this.categoryClickListener = categoryClickListener
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCategorySelectAdapter.ViewHolder {
        val binding = ItemFavoriteCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteCategorySelectAdapter.ViewHolder, position: Int) {
        holder.bind(categoryList[position])

        /*holder.itemView.setOnClickListener {
            categoryClickListener.onItemClick(categoryList[position], isClicked)
        }*/
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class ViewHolder(val binding: ItemFavoriteCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryList: String) {
            binding.itemFavoriteCategoryTv.text = categoryList
        }
    }
}

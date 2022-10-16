package com.cmccx.moge.presentation.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cmccx.moge.databinding.ItemFeedRoundProfileBinding
import com.cmccx.moge.databinding.ItemQuizCardBinding

class FeedProfileAdapter() : RecyclerView.Adapter<FeedProfileAdapter.ViewHolder>() {

    private val profileList = arrayListOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedProfileAdapter.ViewHolder {
        val view = ItemFeedRoundProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedProfileAdapter.ViewHolder, position: Int) {
        val curItem = profileList[position]

        holder.profileName.text = curItem
    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    init {
        profileList.add("머지1")
        profileList.add("머지2")
        profileList.add("머지3")
        profileList.add("머지4")
        profileList.add("머지5")
        profileList.add("머지6")
        profileList.add("머지7")
        profileList.add("머지8")

    }

    inner class ViewHolder(binding: ItemFeedRoundProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        val profileName = binding.itemFeedRoundProfileNameTv
    }
}
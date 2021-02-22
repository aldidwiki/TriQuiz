package com.aldidwiki.myquizapp.adapter

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity
import javax.inject.Inject

class LeaderBoardAdapter @Inject constructor()
    : BaseAdapter<UserEntity>(DIFF_UTIL) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_leaderboard

    override fun onBindViewHolder(holder: BaseViewHolder<UserEntity>, position: Int) {
        super.onBindViewHolder(holder, position)
        val tvRank = holder.itemView.findViewById<TextView>(R.id.tv_rank)
        tvRank.text = (position + 1).toString()
    }
}
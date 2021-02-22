package com.aldidwiki.myquizapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldidwiki.myquizapp.BR
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity
import javax.inject.Inject

class LeaderBoardAdapter @Inject constructor()
    : ListAdapter<UserEntity, LeaderBoardAdapter.LeaderBoardViewHolder>(DIFF_UTIL) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderBoardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_leaderboard, parent, false)
        return LeaderBoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderBoardViewHolder, position: Int) {
        with(holder) {
            bind(getItem(position), position)
        }
    }

    inner class LeaderBoardViewHolder(private val binding: ViewDataBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: UserEntity, position: Int) {
            val tvRank = binding.root.findViewById<TextView>(R.id.tv_rank)
            tvRank.text = (position + 1).toString()

            binding.apply {
                setVariable(BR.user, items)
                executePendingBindings()
            }
        }
    }
}
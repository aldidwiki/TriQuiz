package com.aldidwiki.myquizapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.databinding.ItemFinalResultBinding
import javax.inject.Inject

class AchievementAdapter @Inject constructor()
    : ListAdapter<QuestionEntity, AchievementAdapter.AchievementViewHolder>(DIFF_UTIL) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<QuestionEntity>() {
            override fun areItemsTheSame(oldItem: QuestionEntity, newItem: QuestionEntity): Boolean {
                return oldItem.question == newItem.question
            }

            override fun areContentsTheSame(oldItem: QuestionEntity, newItem: QuestionEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemFinalResultBinding>(layoutInflater,
                R.layout.item_final_result, parent, false)
        return AchievementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        with(holder) {
            bind(getItem(position))
        }
    }

    inner class AchievementViewHolder(private val binding: ItemFinalResultBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QuestionEntity) {
            binding.apply {
                tempData = item
                executePendingBindings()
            }
        }
    }
}
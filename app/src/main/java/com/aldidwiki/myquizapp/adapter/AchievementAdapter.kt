package com.aldidwiki.myquizapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import javax.inject.Inject

class AchievementAdapter @Inject constructor()
    : BaseAdapter<QuestionEntity>(DIFF_UTIL) {
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

    override fun getItemViewType(position: Int): Int = R.layout.item_achievement
}
package com.aldidwiki.myquizapp.presentation.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.aldidwiki.myquizapp.BR
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.domain.model.QuestionDomainModel
import javax.inject.Inject

class AchievementAdapter @Inject constructor()
    : BaseAdapter<QuestionDomainModel>(DIFF_UTIL) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<QuestionDomainModel>() {
            override fun areItemsTheSame(oldItem: QuestionDomainModel, newItem: QuestionDomainModel): Boolean {
                return oldItem.question == newItem.question
            }

            override fun areContentsTheSame(oldItem: QuestionDomainModel, newItem: QuestionDomainModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override val viewId: Int
        get() = R.layout.item_achievement

    override fun onBind(binding: ViewDataBinding, position: Int) {
        binding.setVariable(BR.tempData, getItem(position))
    }

    override fun itemClickCallback(item: QuestionDomainModel) {}
}
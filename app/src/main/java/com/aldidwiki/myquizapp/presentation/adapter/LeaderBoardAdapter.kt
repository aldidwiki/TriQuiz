package com.aldidwiki.myquizapp.presentation.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.aldidwiki.myquizapp.BR
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.domain.model.UserDomainModel
import javax.inject.Inject

class LeaderBoardAdapter @Inject constructor()
    : BaseAdapter<UserDomainModel>(DIFF_UTIL) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<UserDomainModel>() {
            override fun areItemsTheSame(oldItem: UserDomainModel, newItem: UserDomainModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserDomainModel, newItem: UserDomainModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override val viewId: Int
        get() = R.layout.item_leaderboard

    override fun onBind(binding: ViewDataBinding, position: Int) {
        binding.setVariable(BR.user, getItem(position))
        binding.setVariable(BR.itemPosition, position)
    }

    override fun itemClickCallback(item: UserDomainModel) {}
}
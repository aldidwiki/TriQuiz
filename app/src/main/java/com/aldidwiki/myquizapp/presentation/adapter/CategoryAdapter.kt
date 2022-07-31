package com.aldidwiki.myquizapp.presentation.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.aldidwiki.myquizapp.BR
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.domain.model.CategoryDomainModel
import javax.inject.Inject

class CategoryAdapter @Inject constructor()
    : BaseAdapter<CategoryDomainModel>(DIFF_UTIL) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<CategoryDomainModel>() {
            override fun areItemsTheSame(oldItem: CategoryDomainModel, newItem: CategoryDomainModel): Boolean {
                return oldItem.categoryId == newItem.categoryId
            }

            override fun areContentsTheSame(oldItem: CategoryDomainModel, newItem: CategoryDomainModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override val viewId: Int
        get() = R.layout.item_category

    override fun onBind(binding: ViewDataBinding, position: Int) {
        binding.setVariable(BR.categoryData, getItem(position))
    }

    override fun itemClickCallback(item: CategoryDomainModel) {
        onItemClickCallback.onItemClicked(item)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(item: CategoryDomainModel)
    }
}
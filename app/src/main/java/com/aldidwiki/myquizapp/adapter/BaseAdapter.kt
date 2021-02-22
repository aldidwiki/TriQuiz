package com.aldidwiki.myquizapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldidwiki.myquizapp.BR
import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity
import com.aldidwiki.myquizapp.data.source.remote.entity.TriviaCategoriesItem

abstract class BaseAdapter<E>(diffUtil: DiffUtil.ItemCallback<E>)
    : ListAdapter<E, BaseAdapter.BaseViewHolder<E>>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<E> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil
                .inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<E>, position: Int) {
        with(holder) {
            bind(getItem(position))
        }
    }

    class BaseViewHolder<T>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            binding.apply {
                when (item) {
                    is TriviaCategoriesItem -> setVariable(BR.categoryData, item)
                    is QuestionEntity -> setVariable(BR.tempData, item)
                    is UserEntity -> setVariable(BR.user, item)
                }
                executePendingBindings()
            }
        }
    }
}
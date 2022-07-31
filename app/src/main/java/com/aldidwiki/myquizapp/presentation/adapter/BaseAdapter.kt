package com.aldidwiki.myquizapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<E>(diffUtil: DiffUtil.ItemCallback<E>)
    : ListAdapter<E, BaseAdapter.BaseViewHolder>(diffUtil) {

    protected abstract val viewId: Int
    protected abstract fun onBind(binding: ViewDataBinding, position: Int)
    protected abstract fun itemClickCallback(item: E)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil
                .inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind {
            onBind(it, position)
            it.executePendingBindings()
        }

        holder.itemView.setOnClickListener { itemClickCallback(getItem(holder.adapterPosition)) }
    }

    override fun getItemViewType(position: Int) = viewId

    class BaseViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(onBind: (ViewDataBinding) -> Unit) {
            onBind.invoke(binding)
        }
    }
}
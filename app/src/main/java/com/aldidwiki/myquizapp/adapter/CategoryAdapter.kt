package com.aldidwiki.myquizapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aldidwiki.myquizapp.BR
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.data.source.remote.entity.TriviaCategoriesItem
import java.util.*
import javax.inject.Inject

class CategoryAdapter @Inject constructor()
    : ListAdapter<TriviaCategoriesItem, CategoryAdapter.CategoryViewHolder>(DIFF_UTIL) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<TriviaCategoriesItem>() {
            override fun areItemsTheSame(oldItem: TriviaCategoriesItem, newItem: TriviaCategoriesItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TriviaCategoriesItem, newItem: TriviaCategoriesItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil
                .inflate<ViewDataBinding>(layoutInflater, R.layout.item_category, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        with(holder) {
            bind(getItem(position))
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(getItem(adapterPosition))
            }

            Random().apply {
                val color = Color.argb(255, nextInt(256), nextInt(256), nextInt(256))
                itemView.setBackgroundColor(color)
            }
        }
    }

    inner class CategoryViewHolder(private val binding: ViewDataBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TriviaCategoriesItem) {
            binding.apply {
                setVariable(BR.categoryData, item)
                executePendingBindings()
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(item: TriviaCategoriesItem)
    }
}
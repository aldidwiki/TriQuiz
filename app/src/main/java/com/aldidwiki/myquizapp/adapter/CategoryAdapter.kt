package com.aldidwiki.myquizapp.adapter

import android.graphics.Color
import androidx.recyclerview.widget.DiffUtil
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.data.source.remote.entity.TriviaCategoriesItem
import java.util.*
import javax.inject.Inject

class CategoryAdapter @Inject constructor()
    : BaseAdapter<TriviaCategoriesItem>(DIFF_UTIL) {
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

    override fun onBindViewHolder(holder: BaseViewHolder<TriviaCategoriesItem>, position: Int) {
        super.onBindViewHolder(holder, position)
        with(holder) {
            Random().apply {
                val color = Color.argb(255, nextInt(256), nextInt(256), nextInt(256))
                itemView.setBackgroundColor(color)
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(getItem(adapterPosition))
            }
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_category

    interface OnItemClickCallback {
        fun onItemClicked(item: TriviaCategoriesItem)
    }
}
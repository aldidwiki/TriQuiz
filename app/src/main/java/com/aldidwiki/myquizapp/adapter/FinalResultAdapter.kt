package com.aldidwiki.myquizapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import javax.inject.Inject

class FinalResultAdapter @Inject constructor() :
        RecyclerView.Adapter<FinalResultAdapter.FinalResultViewHolder>() {
    private lateinit var items: List<QuestionEntity>
    fun setData(items: List<QuestionEntity>) {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinalResultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_final_result, parent, false)
        return FinalResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: FinalResultViewHolder, position: Int) {
        holder.bind(items, position)
    }

    override fun getItemCount(): Int = items.size

    inner class FinalResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(items: List<QuestionEntity>, position: Int) {
            with(itemView) {
                val tvQuestion = findViewById<TextView>(R.id.tv_question)
                val tvAnswer = findViewById<TextView>(R.id.tv_correct_answers)

                tvQuestion.text = items[position].question
                tvAnswer.text = items[position].correctAnswer
            }
        }
    }
}
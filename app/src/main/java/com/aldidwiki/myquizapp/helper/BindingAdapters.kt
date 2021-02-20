package com.aldidwiki.myquizapp.helper

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.aldidwiki.myquizapp.R

@BindingAdapter("app:html_decode")
fun htmlDecode(textView: TextView, question: String?) {
    textView.text = question?.let { decodeHtml(it) }
}

@BindingAdapter("app:isCorrect")
fun isCorrect(textView: TextView, isCorrect: Boolean) {
    if (isCorrect)
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.correctAnswerColor))
    else textView.setTextColor(ContextCompat.getColor(textView.context, R.color.wrongAnswerColor))
}
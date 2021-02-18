package com.aldidwiki.myquizapp.helper

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:html_decode")
fun htmlDecode(textView: TextView, question: String?) {
    textView.text = question?.let { decodeHtml(it) }
}
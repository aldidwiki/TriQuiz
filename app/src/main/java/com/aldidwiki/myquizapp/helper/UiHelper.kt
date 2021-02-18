package com.aldidwiki.myquizapp.helper

import android.view.View
import androidx.core.text.HtmlCompat

fun View.show(state: Boolean) {
    this.visibility = if (state) View.VISIBLE else View.GONE
}

fun decodeHtml(text: String): String =
        HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
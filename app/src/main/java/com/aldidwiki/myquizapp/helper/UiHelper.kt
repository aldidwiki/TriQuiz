package com.aldidwiki.myquizapp.helper

import android.view.View

fun View.show(state: Boolean) {
    this.visibility = if (state) View.VISIBLE else View.GONE
}
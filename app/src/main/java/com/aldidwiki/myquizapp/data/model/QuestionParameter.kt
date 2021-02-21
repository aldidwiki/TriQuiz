package com.aldidwiki.myquizapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionParameter(
        val token: String,
        val categoryId: Int,
        val difficulty: String?
) : Parcelable

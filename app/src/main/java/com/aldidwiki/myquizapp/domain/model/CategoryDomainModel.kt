package com.aldidwiki.myquizapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDomainModel(
        val categoryName: String,
        val categoryId: Int,
        val categoryBgColor: Int
) : Parcelable

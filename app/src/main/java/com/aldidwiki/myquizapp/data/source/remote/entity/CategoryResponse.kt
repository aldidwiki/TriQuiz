package com.aldidwiki.myquizapp.data.source.remote.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CategoryResponse(

		@field:SerializedName("trivia_categories")
		val triviaCategories: List<TriviaCategoriesItem>
)

@Parcelize
data class TriviaCategoriesItem(

		@field:SerializedName("name")
		val name: String,

		@field:SerializedName("id")
		val id: Int
) : Parcelable

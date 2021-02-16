package com.aldidwiki.myquizapp.data.source.remote.entity

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

		@field:SerializedName("trivia_categories")
		val triviaCategories: List<TriviaCategoriesItem>
)

data class TriviaCategoriesItem(

		@field:SerializedName("name")
		val name: String,

		@field:SerializedName("id")
		val id: Int
)

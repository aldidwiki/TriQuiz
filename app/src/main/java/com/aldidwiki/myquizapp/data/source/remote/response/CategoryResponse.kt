package com.aldidwiki.myquizapp.data.source.remote.response

import android.graphics.Color
import com.aldidwiki.myquizapp.domain.model.CategoryDomainModel
import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class CategoryResponse(

        @field:SerializedName("trivia_categories")
        val triviaCategories: List<TriviaCategoriesItem>
)

data class TriviaCategoriesItem(

        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("id")
        val id: Int
) {
    fun toDomainModel(): CategoryDomainModel {
        val color = Random.run {
            Color.argb(255, nextInt(256), nextInt(256), nextInt(256))
        }

        return CategoryDomainModel(
                categoryName = name,
                categoryId = id,
                categoryBgColor = color
        )
    }
}

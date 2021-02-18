package com.aldidwiki.myquizapp.data.source.remote.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class QuestionResponse(

        @field:SerializedName("response_code")
        val responseCode: Int,

        @field:SerializedName("results")
        val results: List<QuestionItems>
)

@Parcelize
data class QuestionItems(

        @field:SerializedName("difficulty")
        val difficulty: String,

        @field:SerializedName("question")
        val question: String,

        @field:SerializedName("correct_answer")
        val correctAnswer: String,

        @field:SerializedName("incorrect_answers")
        val incorrectAnswers: List<String>,

        @field:SerializedName("category")
        val category: String,

        @field:SerializedName("type")
        val type: String
) : Parcelable

package com.aldidwiki.myquizapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aldidwiki.myquizapp.domain.model.QuestionDomainModel

@Entity(tableName = "question_entity")
data class QuestionEntity(
    @PrimaryKey val question: String,
    val correctAnswer: String,
    val isCorrect: Boolean
) {
    fun toDomainModel() = QuestionDomainModel(
        question = question,
        correctAnswer = correctAnswer,
        isCorrect = isCorrect
    )
}
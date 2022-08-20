package com.aldidwiki.myquizapp.domain.model

import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity

data class QuestionDomainModel(
    val question: String,
    val correctAnswer: String,
    val isCorrect: Boolean
) {
    fun toEntity() = QuestionEntity(
        question = question,
        correctAnswer = correctAnswer,
        isCorrect = isCorrect
    )
}

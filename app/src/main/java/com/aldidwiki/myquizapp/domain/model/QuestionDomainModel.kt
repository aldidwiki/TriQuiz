package com.aldidwiki.myquizapp.domain.model

data class QuestionDomainModel(
    val question: String,
    val correctAnswer: String,
    val isCorrect: Boolean
)

package com.aldidwiki.myquizapp.data.source.local

import com.aldidwiki.myquizapp.data.source.local.database.LocalService
import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val localService: LocalService) {
    fun getTempQuestions() = localService.getTempQuestion()
    fun getUsers() = localService.getUsers()

    suspend fun insertQuestion(questionEntity: QuestionEntity) = localService.insertQuestion(questionEntity)
    suspend fun insertUser(userEntity: UserEntity) = localService.insertUser(userEntity)

    suspend fun clearQuestions() = localService.clearQuestionEntity()
    suspend fun clearLeaderboards() = localService.clearLeaderBoards()
}
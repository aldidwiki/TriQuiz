package com.aldidwiki.myquizapp.domain.usecase

import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity
import com.aldidwiki.myquizapp.data.source.remote.network.ApiResponse
import com.aldidwiki.myquizapp.data.source.remote.response.QuestionItems
import com.aldidwiki.myquizapp.data.source.remote.response.TokenResponse
import com.aldidwiki.myquizapp.domain.model.CategoryDomainModel
import com.aldidwiki.myquizapp.domain.model.QuestionDomainModel
import com.aldidwiki.myquizapp.domain.model.UserDomainModel
import kotlinx.coroutines.flow.Flow

interface AppUseCase {
    fun getCategories(): Flow<ApiResponse<List<CategoryDomainModel>>>

    fun getQuestions(token: String, categoryId: Int, difficulty: String?): Flow<ApiResponse<List<QuestionItems>>>

    fun getTempResults(): Flow<List<QuestionDomainModel>>

    fun getToken(): Flow<ApiResponse<TokenResponse?>>

    fun getUsers(): Flow<List<UserDomainModel>>

    suspend fun insertQuestion(question: QuestionEntity)

    suspend fun clearQuestionEntity()

    suspend fun insertUser(user: UserEntity)

    suspend fun clearLeaderBoards()
}
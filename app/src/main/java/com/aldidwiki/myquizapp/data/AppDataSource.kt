package com.aldidwiki.myquizapp.data

import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity
import com.aldidwiki.myquizapp.data.source.remote.ApiResponse
import com.aldidwiki.myquizapp.data.source.remote.entity.CategoryResponse
import com.aldidwiki.myquizapp.data.source.remote.entity.QuestionItems
import com.aldidwiki.myquizapp.data.source.remote.entity.TokenResponse
import kotlinx.coroutines.flow.Flow

interface AppDataSource {
    fun getCategories(): Flow<ApiResponse<CategoryResponse>>

    fun getQuestions(token: String, categoryId: Int, difficulty: String?): Flow<ApiResponse<List<QuestionItems>>>

    fun getTempResults(): Flow<List<QuestionEntity>>

    fun getUser(): Flow<UserEntity>

    fun getToken(): Flow<ApiResponse<TokenResponse?>>

    suspend fun insertQuestion(question: QuestionEntity)

    suspend fun clearQuestionEntity()

    suspend fun insertUser(user: UserEntity)
}
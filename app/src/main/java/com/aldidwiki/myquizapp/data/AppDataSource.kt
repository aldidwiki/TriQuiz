package com.aldidwiki.myquizapp.data

import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.data.source.remote.ApiResponse
import com.aldidwiki.myquizapp.data.source.remote.entity.CategoryResponse
import com.aldidwiki.myquizapp.data.source.remote.entity.QuestionItems
import com.aldidwiki.myquizapp.data.source.remote.entity.TokenResponse
import kotlinx.coroutines.flow.Flow

interface AppDataSource {
    fun getCategories(): Flow<ApiResponse<CategoryResponse>>

    fun getQuestions(token: String): Flow<ApiResponse<List<QuestionItems>>>

    fun getTempResults(): Flow<List<QuestionEntity>>

    suspend fun getToken(): TokenResponse?

    suspend fun insertQuestion(question: QuestionEntity)

    suspend fun clearQuestionEntity()
}
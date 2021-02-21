package com.aldidwiki.myquizapp.data.source.remote

import com.aldidwiki.myquizapp.data.source.remote.entity.CategoryResponse
import com.aldidwiki.myquizapp.data.source.remote.entity.QuestionResponse
import com.aldidwiki.myquizapp.data.source.remote.entity.TokenResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteService {
    @GET("api_category.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("api.php?amount=1&type=multiple")
    suspend fun getQuestions(@Query("token") token: String,
                             @Query("category") categoryId: Int,
                             @Query("difficulty") difficulty: String?): Response<QuestionResponse>

    @GET("api_token.php")
    suspend fun getToken(@Query("command") command: String): Response<TokenResponse>
}
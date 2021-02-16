package com.aldidwiki.myquizapp.data.source.remote

import com.aldidwiki.myquizapp.data.source.remote.entity.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface RemoteService {
    @GET("api_category.php")
    suspend fun getCategories(): Response<CategoryResponse>
}
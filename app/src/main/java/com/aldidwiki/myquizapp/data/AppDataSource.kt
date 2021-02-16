package com.aldidwiki.myquizapp.data

import com.aldidwiki.myquizapp.data.source.remote.ApiResponse
import com.aldidwiki.myquizapp.data.source.remote.entity.CategoryResponse
import kotlinx.coroutines.flow.Flow

interface AppDataSource {
    fun getCategories(): Flow<ApiResponse<CategoryResponse>>
}
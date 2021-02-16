package com.aldidwiki.myquizapp.data

import com.aldidwiki.myquizapp.data.source.remote.ApiResponse
import com.aldidwiki.myquizapp.data.source.remote.RemoteService
import com.aldidwiki.myquizapp.data.source.remote.entity.CategoryResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class AppRepository @Inject constructor(private val remoteService: RemoteService) : AppDataSource {

    override fun getCategories(): Flow<ApiResponse<CategoryResponse>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = remoteService.getCategories()
            if (response.isSuccessful) response.body()?.let {
                emit(ApiResponse.Success(it))
            } else emit(ApiResponse.Error(response.errorBody().toString()))
        } catch (e: Exception) {
            Timber.e(e)
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(IO).distinctUntilChanged()
}
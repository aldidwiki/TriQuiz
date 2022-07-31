package com.aldidwiki.myquizapp.data.source.remote

import com.aldidwiki.myquizapp.data.source.remote.network.ApiResponse
import com.aldidwiki.myquizapp.data.source.remote.network.RemoteService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val remoteService: RemoteService) {
    fun getCategories() = flow {
        emit(ApiResponse.Loading)
        try {
            val response = remoteService.getCategories()
            if (response.isSuccessful) response.body()?.let {
                emit(ApiResponse.Success(it.triviaCategories.map { triviaCategoriesItem -> triviaCategoriesItem.toDomainModel() }))
            } else emit(ApiResponse.Error(response.errorBody().toString()))
        } catch (e: Exception) {
            Timber.e(e)
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO).distinctUntilChanged()

    fun getToken() = flow {
        emit(ApiResponse.Loading)
        try {
            val tokenResponse = remoteService.getToken("request")
            if (tokenResponse.isSuccessful) tokenResponse.body()?.let {
                emit(ApiResponse.Success(it))
            } else emit(ApiResponse.Error(tokenResponse.errorBody().toString()))
        } catch (e: Exception) {
            Timber.e(e)
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO).distinctUntilChanged()

    fun getQuestion(token: String, categoryId: Int, difficulty: String?) = flow {
        emit(ApiResponse.Loading)
        try {
            val response = remoteService.getQuestions(token, categoryId, difficulty)
            if (response.isSuccessful) response.body()?.let {
                emit(ApiResponse.Success(it.results))
            } else emit(ApiResponse.Error(response.errorBody().toString()))
        } catch (e: Exception) {
            Timber.e(e)
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO).distinctUntilChanged()
}
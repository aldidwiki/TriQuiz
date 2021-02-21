package com.aldidwiki.myquizapp.data

import com.aldidwiki.myquizapp.data.source.local.LocalService
import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity
import com.aldidwiki.myquizapp.data.source.remote.ApiResponse
import com.aldidwiki.myquizapp.data.source.remote.RemoteService
import com.aldidwiki.myquizapp.data.source.remote.entity.CategoryResponse
import com.aldidwiki.myquizapp.data.source.remote.entity.QuestionItems
import com.aldidwiki.myquizapp.data.source.remote.entity.TokenResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class AppRepository @Inject constructor(
        private val remoteService: RemoteService,
        private val localService: LocalService
) : AppDataSource {

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

    override fun getQuestions(token: String, categoryId: Int, difficulty: String?)
            : Flow<ApiResponse<List<QuestionItems>>> = flow {
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
    }.flowOn(IO).distinctUntilChanged()

    override fun getToken(): Flow<ApiResponse<TokenResponse?>> = flow {
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
    }.flowOn(IO).distinctUntilChanged()

    override suspend fun insertQuestion(question: QuestionEntity) {
        localService.insertQuestion(question)
    }

    override suspend fun clearQuestionEntity() {
        localService.clearQuestionEntity()
    }

    override fun getTempResults(): Flow<List<QuestionEntity>> {
        return localService.getTempQuestion()
    }

    override suspend fun insertUser(user: UserEntity) {
        localService.insertUser(user)
    }
}
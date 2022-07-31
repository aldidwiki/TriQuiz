package com.aldidwiki.myquizapp.data

import com.aldidwiki.myquizapp.data.source.local.LocalDataSource
import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity
import com.aldidwiki.myquizapp.data.source.remote.RemoteDataSource
import com.aldidwiki.myquizapp.data.source.remote.network.ApiResponse
import com.aldidwiki.myquizapp.data.source.remote.response.QuestionItems
import com.aldidwiki.myquizapp.data.source.remote.response.TokenResponse
import com.aldidwiki.myquizapp.domain.model.CategoryDomainModel
import com.aldidwiki.myquizapp.domain.model.QuestionDomainModel
import com.aldidwiki.myquizapp.domain.model.UserDomainModel
import com.aldidwiki.myquizapp.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

class AppRepositoryImpl @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource
) : AppRepository {

    override fun getCategories(): Flow<ApiResponse<List<CategoryDomainModel>>> = remoteDataSource.getCategories()

    override fun getQuestions(token: String, categoryId: Int, difficulty: String?)
            : Flow<ApiResponse<List<QuestionItems>>> = remoteDataSource.getQuestion(token, categoryId, difficulty)

    override fun getToken(): Flow<ApiResponse<TokenResponse?>> = remoteDataSource.getToken()

    override suspend fun insertQuestion(question: QuestionEntity) {
        localDataSource.insertQuestion(question)
    }

    override suspend fun clearQuestionEntity() {
        localDataSource.clearQuestions()
    }

    override fun getTempResults(): Flow<List<QuestionDomainModel>> {
        return localDataSource.getTempQuestions().map {
            it.map { questionEntity -> questionEntity.toDomainModel() }
        }
    }

    override suspend fun insertUser(user: UserEntity) {
        localDataSource.insertUser(user)
    }

    override fun getUsers(): Flow<List<UserDomainModel>> {
        return localDataSource.getUsers().map {
            it.map { userEntity -> userEntity.toDomainModel() }
        }
    }

    override suspend fun clearLeaderBoards() {
        localDataSource.clearLeaderboards()
    }
}
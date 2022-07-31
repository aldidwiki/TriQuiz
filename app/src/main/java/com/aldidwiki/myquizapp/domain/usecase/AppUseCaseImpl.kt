package com.aldidwiki.myquizapp.domain.usecase

import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity
import com.aldidwiki.myquizapp.data.source.remote.network.ApiResponse
import com.aldidwiki.myquizapp.data.source.remote.response.QuestionItems
import com.aldidwiki.myquizapp.data.source.remote.response.TokenResponse
import com.aldidwiki.myquizapp.domain.model.CategoryDomainModel
import com.aldidwiki.myquizapp.domain.model.QuestionDomainModel
import com.aldidwiki.myquizapp.domain.model.UserDomainModel
import com.aldidwiki.myquizapp.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppUseCaseImpl @Inject constructor(
        private val appRepository: AppRepository
) : AppUseCase {
    override fun getCategories(): Flow<ApiResponse<List<CategoryDomainModel>>> {
        return appRepository.getCategories()
    }

    override fun getQuestions(token: String, categoryId: Int, difficulty: String?): Flow<ApiResponse<List<QuestionItems>>> {
        return appRepository.getQuestions(token, categoryId, difficulty)
    }

    override fun getTempResults(): Flow<List<QuestionDomainModel>> {
        return appRepository.getTempResults()
    }

    override fun getToken(): Flow<ApiResponse<TokenResponse?>> {
        return appRepository.getToken()
    }

    override fun getUsers(): Flow<List<UserDomainModel>> {
        return appRepository.getUsers()
    }

    override suspend fun insertQuestion(question: QuestionEntity) {
        appRepository.insertQuestion(question)
    }

    override suspend fun clearQuestionEntity() {
        appRepository.clearQuestionEntity()
    }

    override suspend fun insertUser(user: UserEntity) {
        appRepository.insertUser(user)
    }

    override suspend fun clearLeaderBoards() {
        appRepository.clearLeaderBoards()
    }
}
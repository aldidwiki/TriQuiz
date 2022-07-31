package com.aldidwiki.myquizapp.presentation.ui.achievement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aldidwiki.myquizapp.domain.model.UserDomainModel
import com.aldidwiki.myquizapp.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    val getTempResults = appUseCase.getTempResults().asLiveData()

    fun insertUser(userDomainModel: UserDomainModel) {
        viewModelScope.launch { appUseCase.insertUser(userDomainModel.toEntity()) }
    }
}
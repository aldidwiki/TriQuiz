package com.aldidwiki.myquizapp.ui.achievement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aldidwiki.myquizapp.data.AppRepository
import com.aldidwiki.myquizapp.data.source.local.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
    val getTempResults = appRepository.getTempResults().asLiveData()

    fun insertUser(userEntity: UserEntity) {
        viewModelScope.launch { appRepository.insertUser(userEntity) }
    }
}
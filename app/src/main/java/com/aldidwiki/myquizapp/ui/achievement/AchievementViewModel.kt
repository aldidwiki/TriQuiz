package com.aldidwiki.myquizapp.ui.achievement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aldidwiki.myquizapp.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(appRepository: AppRepository) : ViewModel() {
    val getTempResults = appRepository.getTempResults().asLiveData()
}
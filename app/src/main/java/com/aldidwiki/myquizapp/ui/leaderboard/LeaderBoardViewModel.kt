package com.aldidwiki.myquizapp.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aldidwiki.myquizapp.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    fun getUsers() = appRepository.getUsers().asLiveData()

    fun clearLeaderboards() {
        viewModelScope.launch {
            appRepository.clearLeaderBoards()
        }
    }
}
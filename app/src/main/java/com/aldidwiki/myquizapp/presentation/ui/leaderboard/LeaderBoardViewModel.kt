package com.aldidwiki.myquizapp.presentation.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aldidwiki.myquizapp.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {

    fun getUsers() = appUseCase.getUsers().asLiveData()

    fun clearLeaderboards() {
        viewModelScope.launch {
            appUseCase.clearLeaderBoards()
        }
    }
}
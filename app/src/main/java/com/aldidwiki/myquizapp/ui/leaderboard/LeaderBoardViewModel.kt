package com.aldidwiki.myquizapp.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aldidwiki.myquizapp.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    fun getUsers() = appRepository.getUsers().asLiveData()
}
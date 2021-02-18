package com.aldidwiki.myquizapp.ui.pregame

import androidx.lifecycle.ViewModel
import com.aldidwiki.myquizapp.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PreGameViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
    suspend fun getToken() = appRepository.getToken()
}
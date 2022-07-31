package com.aldidwiki.myquizapp.presentation.ui.pregame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aldidwiki.myquizapp.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreGameViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    fun getToken() = appUseCase.getToken().asLiveData()

    fun clearQuestionEntity() {
        viewModelScope.launch { appUseCase.clearQuestionEntity() }
    }
}
package com.aldidwiki.myquizapp.ui.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.aldidwiki.myquizapp.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
    private val token = MutableLiveData<String>()
    fun setToken(token: String) {
        this.token.value = token
    }

    fun getQuestions() = token.switchMap { appRepository.getQuestions(it).asLiveData() }
}
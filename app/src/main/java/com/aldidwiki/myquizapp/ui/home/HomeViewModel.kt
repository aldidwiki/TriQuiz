package com.aldidwiki.myquizapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aldidwiki.myquizapp.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(appRepository: AppRepository) : ViewModel() {

    val getCategories = appRepository.getCategories().asLiveData()
}
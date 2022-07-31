package com.aldidwiki.myquizapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aldidwiki.myquizapp.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(appUseCase: AppUseCase) : ViewModel() {

    val getCategories = appUseCase.getCategories().asLiveData()
}
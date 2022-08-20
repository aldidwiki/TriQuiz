package com.aldidwiki.myquizapp.di

import com.aldidwiki.myquizapp.domain.usecase.AppUseCase
import com.aldidwiki.myquizapp.domain.usecase.AppInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @ViewModelScoped
    @Binds
    abstract fun bindAppUseCase(appInteractor: AppInteractor): AppUseCase
}
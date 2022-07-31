package com.aldidwiki.myquizapp.di

import com.aldidwiki.myquizapp.data.AppRepositoryImpl
import com.aldidwiki.myquizapp.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAppRepository(appRepositoryImpl: AppRepositoryImpl): AppRepository
}
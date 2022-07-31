package com.aldidwiki.myquizapp.di

import android.content.Context
import androidx.room.Room
import com.aldidwiki.myquizapp.data.source.local.database.LocalDatabase
import com.aldidwiki.myquizapp.data.source.local.database.LocalDatabase.Companion.DB_NAME
import com.aldidwiki.myquizapp.data.source.local.database.LocalService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {
    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(context, LocalDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideLocalService(database: LocalDatabase): LocalService =
            database.localService()
}
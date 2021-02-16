package com.aldidwiki.myquizapp.di

import com.aldidwiki.myquizapp.BuildConfig
import com.aldidwiki.myquizapp.data.source.remote.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    fun provideRemoteService(retrofit: Retrofit): RemoteService =
            retrofit.create(RemoteService::class.java)
}
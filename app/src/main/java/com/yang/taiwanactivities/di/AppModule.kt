package com.yang.taiwanactivities.di

import com.yang.taiwanactivities.data.api.ApiService
import com.yang.taiwanactivities.data.api.RetrofitFactory
import com.yang.taiwanactivities.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideActivityApi(): ApiService = RetrofitFactory.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(apiService: ApiService): MainRepository = MainRepository(apiService)
}
package com.amir.newsapiclient.presentation.di

import android.app.Application
import com.amir.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.amir.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import com.amir.newsapiclient.presentation.viewModel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
//10
@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun providesNewsViewModelFactory(
        application: Application,
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(application,getNewsHeadlinesUseCase,getSearchedNewsUseCase)
    }
}
package com.amir.newsapiclient.presentation.di

import com.amir.newsapiclient.domain.repositories.NewsRepositories
import com.amir.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.amir.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
//9
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetNewsHeadLLinesUseCase(newsRepositories: NewsRepositories): GetNewsHeadlinesUseCase {

        return GetNewsHeadlinesUseCase(newsRepositories)
    }


    @Singleton
    @Provides
    fun provideGetSearchedNewsUseCase(newsRepositories: NewsRepositories): GetSearchedNewsUseCase {

        return GetSearchedNewsUseCase(newsRepositories)
    }
}
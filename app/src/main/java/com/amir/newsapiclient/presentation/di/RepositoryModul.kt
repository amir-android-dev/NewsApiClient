package com.amir.newsapiclient.presentation.di

import com.amir.newsapiclient.data.repository.NewsRepositoryImpl
import com.amir.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.amir.newsapiclient.domain.repositories.NewsRepositories
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModul {

    //create a provider function to provide a NewsRepository instance
    @Singleton
    @Provides
    fun provideNewsRepository(newsRemoteDataSource: NewsRemoteDataSource):NewsRepositories{
        return NewsRepositoryImpl(newsRemoteDataSource)
    }
}
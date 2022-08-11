package com.amir.newsapiclient.presentation.di

import com.amir.newsapiclient.data.api.NewsAPIService
import com.amir.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.amir.newsapiclient.data.repository.datasourceimpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    //This function should take an instance of NewsAPIService as a parameter.
    /*Why is that?
    Because this NewsRemoteDataSourceImpl class we created implementing NewsRemoteDataSource interface need
    an instance of NewsAPIService as a constructor parameter.
     */
    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsAPIService: NewsAPIService):NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsAPIService)
    }


}
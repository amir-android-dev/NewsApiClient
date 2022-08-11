package com.amir.newsapiclient.presentation.di

import com.amir.newsapiclient.BuildConfig
import com.amir.newsapiclient.data.api.NewsAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//When we were using pure dagger we created component interfaces and listed these modules there.
//But now, with dagger hilt, we have already created, in built set of components.
//All we need to do it install in.
//with @InstallIn to tell Hilt which Android class each module will be used or installed in.
@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    //a function to provide retrofit instance
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    //a fun to create an instance of newsApiService
    //it takes the retrofit dependency that we create as parameter
    @Singleton
    @Provides
    fun provideNewsAPIService(retrofit: Retrofit): NewsAPIService {
        return retrofit.create(NewsAPIService::class.java)
    }
}
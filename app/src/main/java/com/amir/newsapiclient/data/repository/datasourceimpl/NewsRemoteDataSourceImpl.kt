package com.amir.newsapiclient.data.repository.datasourceimpl

import com.amir.newsapiclient.data.api.NewsAPIService
import com.amir.newsapiclient.data.model.APIResponse
import com.amir.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(  private val newsAPIService: NewsAPIService,
                                 private val country: String,
                                 private val page: Int):NewsRemoteDataSource {

    //a function to communicate with API to impelement the NewsAPIService
    /*Our service interface function has 3 parameters. Country, page and apikey.
    // Since we have already provided the api key we don’t need to worry about that parameter
    but we need the country and page we add them as constructor parameter
    and we need an instance of NEwsAPIService
     */
    override suspend fun getTopHeadLines(): Response<APIResponse> {
      return newsAPIService.getTopHeadLines(country, page)
    }
}
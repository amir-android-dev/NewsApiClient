package com.amir.newsapiclient.data.repository.datasource

import com.amir.newsapiclient.data.model.APIResponse
import retrofit2.Response
//2
interface NewsRemoteDataSource{

    suspend fun getTopHeadLines(country:String, page:Int): Response<APIResponse>
    suspend fun getSearchedNews(country:String,searchQuery:String, page:Int): Response<APIResponse>
}
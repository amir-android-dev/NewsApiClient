package com.amir.newsapiclient.data.api

import com.amir.newsapiclient.BuildConfig
import com.amir.newsapiclient.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//e will define functions to get data from the new api with url end points and query parameters.
interface NewsAPIService {
    //since we use retrofit with coroutine we use suspend modifier for this function
    @GET("/v2/top-headlines")
    suspend fun getTopHeadLines(
        @Query("country")
        country: String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        ApiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>
}
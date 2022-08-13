package com.amir.newsapiclient.data.api

import com.amir.newsapiclient.BuildConfig
import com.amir.newsapiclient.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
//1
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

    //if we read this newsapi.org documentation we will see, they have a request parameter for the search query.
//So, that means if we include search query parameter to the request, server will send the list of search results.
// So, this seems very straight forward. All we need to do is including another parameter to this function.
    @GET("/v2/top-headlines")
    suspend fun getSearchedTopHeadLines(
        @Query("country")
        country: String,
        @Query("q")
        searchQuery:String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        ApiKey: String = BuildConfig.API_KEY
    ): Response<APIResponse>
}
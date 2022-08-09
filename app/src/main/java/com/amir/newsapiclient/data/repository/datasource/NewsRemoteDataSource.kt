package com.amir.newsapiclient.data.repository.datasource

import com.amir.newsapiclient.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource{

    suspend fun getTopHeadLines(): Response<APIResponse>
}
package com.amir.newsapiclient.data.repository

import com.amir.newsapiclient.data.model.APIResponse
import com.amir.newsapiclient.data.model.Article
import com.amir.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.amir.newsapiclient.data.util.Resource
import com.amir.newsapiclient.domain.repositories.NewsRepositories
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(private val newsRemoteDataSource: NewsRemoteDataSource) :
    NewsRepositories {
//Now to implement this getNewsHeadlines function we will use that newly created responseToResult
    override suspend fun getNewsHeadLines(country:String, page:Int): Resource<APIResponse> {
       return responseToResource(newsRemoteDataSource.getTopHeadLines(country, page))
    }
//We are going to use this resource class to keep track of the status of the response returned from the api.
    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
    return Resource.Error(response.message())

    }

    override suspend fun getSearchedNews(searchQuery: String): Resource<APIResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun savedNews(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }
}
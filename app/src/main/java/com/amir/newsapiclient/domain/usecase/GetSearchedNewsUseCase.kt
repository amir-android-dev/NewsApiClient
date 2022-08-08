package com.amir.newsapiclient.domain.usecase

import com.amir.newsapiclient.data.model.APIResponse
import com.amir.newsapiclient.data.util.Resource
import com.amir.newsapiclient.domain.repositories.NewsRepositories

class GetSearchedNewsUseCase(private val newsRepositories: NewsRepositories) {

    suspend fun execute(searchQuery:String): Resource<APIResponse> {
        return newsRepositories.getSearchedNews(searchQuery)
    }
}
package com.amir.newsapiclient.domain.usecase

import com.amir.newsapiclient.data.model.APIResponse
import com.amir.newsapiclient.data.util.Resource
import com.amir.newsapiclient.domain.repositories.NewsRepositories

class GetSearchedNewsUseCase(private val newsRepositories: NewsRepositories) {
//6
    suspend fun execute(country:String,searchQuery:String,page:Int): Resource<APIResponse> {
        return newsRepositories.getSearchedNews(country, searchQuery, page)
    }
}
package com.amir.newsapiclient.domain.usecase

import com.amir.newsapiclient.data.model.Article
import com.amir.newsapiclient.domain.repositories.NewsRepositories
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepositories: NewsRepositories) {

    fun execute():Flow<List<Article>>{

        return newsRepositories.getSavedNews()
    }
}
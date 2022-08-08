package com.amir.newsapiclient.domain.usecase

import com.amir.newsapiclient.data.model.APIResponse
import com.amir.newsapiclient.data.model.Article
import com.amir.newsapiclient.data.util.Resource
import com.amir.newsapiclient.domain.repositories.NewsRepositories

class DeleteSavedNewsUseCase(private val newsRepositories: NewsRepositories) {

//    suspend fun execute(article: Article) {
//        return newsRepositories.deleteNews(article)
//    }
    //oder
    suspend fun execute(article: Article)= newsRepositories.deleteNews(article)


}
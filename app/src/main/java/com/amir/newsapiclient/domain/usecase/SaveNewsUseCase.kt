package com.amir.newsapiclient.domain.usecase

import com.amir.newsapiclient.data.model.Article
import com.amir.newsapiclient.domain.repositories.NewsRepositories

class SaveNewsUseCase(private val newsRepositories: NewsRepositories) {

//    suspend fun execute(article: Article){
//        return newsRepositories.savedNews(article)
//    }
    //oder
    suspend fun execute(article: Article)= newsRepositories.savedNews(article)

}
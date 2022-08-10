package com.amir.newsapiclient.domain.repositories


import com.amir.newsapiclient.data.model.APIResponse
import com.amir.newsapiclient.data.model.Article
import com.amir.newsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow


interface NewsRepositories {
//That means it can be a loading api response, successful  api response or a failed api response.
    suspend fun getNewsHeadLines(country:String, page:Int):Resource<APIResponse>

    suspend fun getSearchedNews(searchQuery:String):Resource<APIResponse>

    suspend fun savedNews(article: Article)

    suspend fun deleteNews(article: Article)
/*a function to return a list of Article from db, we want a real time update and
    we want to get notified of every change that happens in the Article’s table. So use LiveData*/
//   - suspend fun getSavedNews():List<Article>
    /*this work for most cases but is not good practice for MVVM
    //because LiveData is a lifecycle of data holder class
    We should always try to use live data in view models and observe them from activities and fragments.
    is not recommended to use in repositories it causes unexpected threading issues
  -     suspend fun getSavedNews():LiveData<List<Article>>
     */

    /*Before Kotlin Coroutines, most of the developers used Rxjava to get data from the repositories to view model
 and then used live data to emit those data from the view models to acitivities and fragments.

 now we have coroutines flow api. Flow API in Kotlin is a better way to handle the stream of data asynchronously .
Room library allows us to get the data as a flow.

Since this function returns a data stream,we don’t need to write this function as a suspending function.
   We don’t want to pause this function and resume it at a later time.
     */
 fun getSavedNews(): Flow<List<Article>>
}
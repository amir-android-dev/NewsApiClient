package com.amir.newsapiclient.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amir.newsapiclient.data.model.APIResponse
import com.amir.newsapiclient.data.util.Resource
import com.amir.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.amir.newsapiclient.domain.usecase.GetSearchedNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

//7
//in this class we are going to write a function to get the list of news head lines
//we will get it as a mutable liveData of type resource
class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase
) : AndroidViewModel(app) {

    val newsHeadlines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    /*create get breaking news function. This should have string country name and int page number as parameters.
     Inside this function we are going to execute a networking task. So we need to do it using a separate thread.
     */
    fun getNewsHeadLines(country: String, page: Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadlines.postValue(Resource.Loading())
        try {


            if (isNetworkAvailable(app)) {

                val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                newsHeadlines.postValue(apiResult)
            } else {
                newsHeadlines.postValue(Resource.Error("Internet is not Available"))
            }
        } catch (e: Exception) {
            newsHeadlines.postValue(Resource.Error(e.message.toString()))
        }
    }

    //a function to check the internet availability
    //the code inside of this function is very popular command code
    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    //search
    val searchedNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    fun searchNews(country: String, searchQuery: String, page: Int) = viewModelScope.launch {
        searchedNews.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val response = getSearchedNewsUseCase.execute(country, searchQuery, page)
                searchedNews.postValue(response)
            } else {
                searchedNews.postValue(Resource.Error("No Internet connection"))
            }
        } catch (e: Exception) {
    searchedNews.postValue(Resource.Error(e.message.toString()))
        }
    }


}
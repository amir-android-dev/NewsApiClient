package com.amir.newsapiclient.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amir.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.amir.newsapiclient.domain.usecase.GetSearchedNewsUseCase
//8
class NewsViewModelFactory(private val app: Application, private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase, private val getSearchedNewsUseCase: GetSearchedNewsUseCase) :ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      return NewsViewModel(app, getNewsHeadlinesUseCase,getSearchedNewsUseCase) as T
    }
}

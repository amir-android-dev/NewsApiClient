package com.amir.newsapiclient.domain.usecase

import com.amir.newsapiclient.data.model.APIResponse
import com.amir.newsapiclient.data.util.Resource
import com.amir.newsapiclient.domain.repositories.NewsRepositories

/*
In MVVM Clean architecture,
 Views means activities and fragments have references to view models,
  View models have references to use cases,
  Use cases have references to repositories.
 */
// adding a reference to this repository as a constructor parameter to the use case class.
class GetNewsHeadlinesUseCase(private val newsRepositories: NewsRepositories) {

    //as usual an execute function
    /*
    don’t mis understand this.
    We don’t have to always return the same value taken from the repository.
     We could actually code some business logic here.
      We could get some data from the repository, modify it and return as another type.
      But in this project, for this scenario we just return the data taken from the repository
     */
    suspend fun execute(): Resource<APIResponse> {

        return newsRepositories.getNewsHeadLines()
    }

}
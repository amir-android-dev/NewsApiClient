package com.amir.newsapiclient.data.util

/*
let’s move one step further and consider the state of the api response.
 Considering state is very useful for error handing and to provide more interactive experience to the user.
Usually an API response has 3 states. Loading state, Success state and error state.

google has provided a generic class that holds a value with its loading status.
We can just use that utility class for all our projects.

Inside this Resource class we are going to define 3 classes for Loading, Success and error states. */
/*This generic resource object can have any type.

So this time, type of the resource will be API response.
 That means it can be a loading api response, successful api response or a failed api response. */

/*In Kotlin , sealed classes allow us to represent hierarchies in the same file or as nested classes .*/
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

}
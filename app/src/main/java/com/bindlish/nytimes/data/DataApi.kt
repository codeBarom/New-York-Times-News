package com.bindlish.nytimes.data

import androidx.lifecycle.LiveData
import com.bindlish.nytimes.data.network.Resource
import com.bindlish.nytimes.di.ApiModule
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {

    @GET("mostpopular/v2/viewed/7.json")
    fun fetchNews(@Query("api-key") apiKey : String = ApiModule.API_KEY): LiveData<Resource<NewsSource>>
}
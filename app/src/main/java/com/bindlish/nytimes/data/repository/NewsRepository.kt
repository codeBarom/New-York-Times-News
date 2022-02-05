package com.bindlish.nytimes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bindlish.nytimes.data.DataApi
import com.bindlish.nytimes.data.NewsData
import com.bindlish.nytimes.data.NewsSource
import com.bindlish.nytimes.data.network.Resource
import com.bindlish.nytimes.db.NewsDao
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val dataDao: NewsDao,
    private val dataApi: DataApi,
    private val appRequestExecutors: AppRequestExecutors = AppRequestExecutors()
) {

    companion object {
        // cache timeout is 2 hours for this usecase
        val TIMEOUT = TimeUnit.HOURS.toMillis(2)
    }

    // fetch data, param is boolean which is to fetch api data forcefully
    fun getData(forceRefresh: Boolean = false): MutableLiveData<Resource<List<NewsData>?>> =
        object : NetworkBoundResource<List<NewsData>, NewsSource>(appRequestExecutors) {
            override fun saveCallResult(item: NewsSource) {
                dataDao.deleteAndInsertWithTimeStamp(item.results)
            }

            override fun shouldFetch(data: List<NewsData>?): Boolean =
                data == null || data.isEmpty() || isCacheTimedOut(data) || forceRefresh

            override fun loadFromDb(): LiveData<List<NewsData>> = dataDao.getNews()

            override fun createCall(): LiveData<Resource<NewsSource>> =
                dataApi.fetchNews()

        }.asLiveData()

    fun getNewsById(id : Long) : LiveData<NewsData> = dataDao.getNewsById(id)

    // method to check cache timeout of a request
    private fun isCacheTimedOut(data: List<NewsData>?): Boolean {
        data?.let {
            val lastFetched = it[0].createdAt
            val now = System.currentTimeMillis()
            if ((now - lastFetched) > TIMEOUT) {
                return true
            }
        }
        return false
    }
}
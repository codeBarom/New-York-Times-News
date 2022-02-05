package com.codebaron.nytimes

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.codebaron.nytimes.data.NewsData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object MockUtils {

    fun mockRepositories(): List<NewsData> {
        val newsList = ArrayList<NewsData>()
        val news = NewsData(
            byline = "By TAFFY BRODESSER-AKNER",
            title = "This Tom Hanks Story Will Help You Feel Less Bad",
            url = "https://www.nytimes.com/2019/11/13/movies/tom-hanks-mister-rogers.html",
            type = "Article",
            published_date = "2019-11-13",
            source = "The New York Times",
            abstract = "Hanks is playing Mister Rogers in a new movie and is just as nice as you think he is. Please read this article anyway.",
            media = emptyList(),
            createdAt = System.currentTimeMillis()
        )
        newsList.add(news)
        return newsList
    }

    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T?) {
                data[0] = t
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        return data[0] as T
    }
}
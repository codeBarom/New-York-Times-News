package com.codebaron.nytimes.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.codebaron.nytimes.data.NewsData
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
open class DatabaseTest {

    private lateinit var newsDb: NewsDatabase
    private lateinit var newsDao: NewsDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        newsDb = Room.inMemoryDatabaseBuilder(
            context,
            NewsDatabase::class.java
        ).build()
        newsDao = newsDb.newsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        newsDb.close()
    }

    @Test
    fun should_insert_news() {
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
        val rows = newsDao.insertNews(listOf(news))
        val newsTest = getValue(newsDao.getNews())

        Assert.assertEquals(1, rows.size)
        Assert.assertEquals(1, newsTest.size)
        assert(newsTest.isNotEmpty())
    }

    @Test
    fun compare_inserted_news() {
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
        newsDao.insertNews(listOf(news))
        val newsTest = getValue(newsDao.getNews())

        Assert.assertEquals(1, newsTest.size)
        assert(newsTest.isNotEmpty())

        val news1 = newsTest[0]
        Assert.assertEquals(news.title, news1.title)
        Assert.assertEquals(news.source, news1.source)
        Assert.assertEquals(news.published_date, news1.published_date)
        Assert.assertEquals(news.url, news1.url)
    }

    @Test
    fun should_clear_news() {
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
        newsDao.insertNews(listOf(news))
        newsDao.deleteAll()
        assert(getValue(newsDao.getNews()).isEmpty())
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
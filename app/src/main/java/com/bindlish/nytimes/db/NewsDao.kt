package com.bindlish.nytimes.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bindlish.nytimes.data.NewsData

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getNews(): LiveData<List<NewsData>>

    @Query("SELECT * FROM news WHERE id = :id")
    fun getNewsById(id : Long): LiveData<NewsData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(repos: List<NewsData>): List<Long>

    @Query("DELETE FROM news")
    fun deleteAll()

    @Transaction
    fun deleteAndInsertRepos(repos: List<NewsData>) {
        deleteAll()
        insertNews(repos)
    }

    @Transaction
    fun deleteAndInsertWithTimeStamp(repos: List<NewsData>) {
        deleteAndInsertRepos(repos.apply {
            for (data in this) {
                data.createdAt = System.currentTimeMillis()
            }
        })
    }
}
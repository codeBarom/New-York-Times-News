package com.bindlish.nytimes.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "news")
data class NewsData(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @SerializedName("byline")
    var byline: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("type")
    var type: String?,
    @SerializedName("published_date")
    var published_date: String?,
    @SerializedName("source")
    var source: String,
    @SerializedName("abstract")
    var abstract: String,
    @SerializedName("media")
    var media : List<Media>?,
    var createdAt: Long
){
    constructor() : this(0,"","","","","","","", emptyList(),0L)
}
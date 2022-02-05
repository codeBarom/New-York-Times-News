package com.bindlish.nytimes.data

import com.bindlish.nytimes.MediaMetaData
import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("type")
    var type : String,
    @SerializedName("media-metadata")
    var mediaList : List<MediaMetaData>?
)
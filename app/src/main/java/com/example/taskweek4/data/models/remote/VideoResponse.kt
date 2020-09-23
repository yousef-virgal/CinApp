package com.example.taskweek4.data.models.remote

import com.google.gson.annotations.SerializedName

data class VideoResponse(@SerializedName("results")
                         val results:List<VideoObject>)

data class VideoObject(
    @SerializedName("key")
    val videoKey:String,
    @SerializedName("name")
    val videoName:String,
    @SerializedName("site")
    val siteName:String,
    @SerializedName("size")
    val sizeType:Int,
    @SerializedName("type")
    val movieType:String)
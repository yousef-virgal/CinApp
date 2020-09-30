package com.example.taskweek4.data.models.remote

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("results")
    val myResults:List<ReviewObject>)

data class ReviewObject(
    @SerializedName("author")
    val authorName:String,
    @SerializedName("content")
    val contentText:String)
package com.example.taskweek4.data.models.remote

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("result")
    val results:List<ReviewObject>,
    @SerializedName("total_pages")
    val totalPages:Int,
    @SerializedName("total_results")
    val totalResults:Int,
    @SerializedName("page")
    val Page:Int)

data class ReviewObject(
    @SerializedName("author")
    val authorName:String,
    @SerializedName("content")
    val contentText:String)
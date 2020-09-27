package com.example.taskweek4.data.models.remote

import com.google.gson.annotations.SerializedName

data class MovieResponse(val results:List<MovieObject>,val total_pages:Int)

data class MovieObject(
    @SerializedName("id")
    val movieId:Int,
    @SerializedName("vote_average")
    val voteAverage:Double,
    @SerializedName("title")
    val title:String,
    @SerializedName("release_date")
    val releaseDate:String,
    @SerializedName("backdrop_path")
    val backdropPath:String,
    @SerializedName("poster_path")
    val posterPath:String,
    @SerializedName("overview")
    val overview:String,
    @SerializedName("media_type")
    val mediaType:String,
    @SerializedName("vote_count")
    val voteCount:Double,
    @SerializedName("name")
    val name:String)

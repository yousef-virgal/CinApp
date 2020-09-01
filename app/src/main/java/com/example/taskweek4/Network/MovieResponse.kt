package com.example.taskweek4.Network

import com.google.gson.annotations.SerializedName

data class MovieResponse(val results:List<MovieObject>)

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
    val voteCount:Double)

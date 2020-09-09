package com.example.taskweek4.data.models.ui

import com.example.taskweek4.data.models.remote.MovieResponse

class Mapper {
    fun mapData(movieResponse: MovieResponse): List<Movies> {
        val movies = mutableListOf<Movies>()
        movieResponse.results.forEach {
            movies.add(Movies(it.movieId,it.voteAverage,it.title,
                it.releaseDate,it.backdropPath,it.posterPath,it.overview
                ,it.mediaType,it.voteCount,it.name))
        }
        return movies
    }
}
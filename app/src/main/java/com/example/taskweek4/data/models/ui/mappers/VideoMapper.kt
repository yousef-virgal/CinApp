package com.example.taskweek4.data.models.ui.mappers

import com.example.taskweek4.data.models.remote.MovieResponse
import com.example.taskweek4.data.models.remote.VideoResponse
import com.example.taskweek4.data.models.ui.objects.Movies
import com.example.taskweek4.data.models.ui.objects.Videos

class VideoMapper {

    fun mapData(videoResponse: VideoResponse): List<Videos> {
        val videos = mutableListOf<Videos>()
        videoResponse.results.forEach {
            videos.add(
                Videos(it.videoKey, it.videoName, it.siteName, it.sizeType, it.movieType)
            )
        }
        return videos
    }
}
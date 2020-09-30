package com.example.taskweek4.data.models.ui.mappers

import com.example.taskweek4.data.models.remote.ReviewResponse
import com.example.taskweek4.data.models.ui.objects.Reviews
import com.example.taskweek4.data.models.ui.objects.Videos

class ReviewMapper {
    fun mapData(reviewResponse: ReviewResponse): List<Reviews> {
        val reviews = mutableListOf<Reviews>()
        reviewResponse.myResults.forEach {
            reviews.add(
                Reviews(it.authorName, it.contentText)
            )
        }
        return reviews
    }
}
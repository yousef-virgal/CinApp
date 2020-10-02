package com.example.taskweek4.ui.favouriteFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.taskweek4.data.models.ui.objects.Movies
import com.example.taskweek4.repository.MovieRepo


class FavoriteViewModel(application: Application) : AndroidViewModel(application) {


    fun getFavourites():List<Movies>{
       return MovieRepo.getFavorites()
    }
}
package com.example.taskweek4.ui.favouriteFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.repository.MovieRepo


class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val _favoriteLiveData : MutableLiveData<List<Movies>> by lazy { MutableLiveData<List<Movies>>() }
    private val _errorLiveData : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val favoriteLiveData: LiveData<List<Movies>>
        get() = _favoriteLiveData
    val errorLiveData: LiveData<String>
        get() = _errorLiveData
    var page=1
    fun changeMovieFromViewModel(repo: MovieRepo,movies: Movies){
        return repo.changeMovie(movies)
    }


}
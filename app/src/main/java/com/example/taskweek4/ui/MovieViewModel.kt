package com.example.taskweek4.ui
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.repository.MovieCallBack
import com.example.taskweek4.repository.MovieRepo
import android.app.Application

class MovieViewModel(application: Application) : AndroidViewModel(application),MovieCallBack
{


    val _movieLiveData : MutableLiveData<List<Movies>> by lazy { MutableLiveData<List<Movies>>() }
    val movieLiveData: LiveData<List<Movies>>
        get() = _movieLiveData
    private lateinit var movieData: List<Movies>

    private var currentSpinner = "movie"
    init {

    }



fun loadMovieData(spinnerData:String = " "){
    if (spinnerData == currentSpinner && this::movieData.isInitialized) {
        _movieLiveData.value = movieData
        return
    }

        currentSpinner = spinnerData
    MovieRepo.getData(this,currentSpinner)


}
    override fun isReady(movies: List<Movies>) {
        movieData = movies
    }


}
package com.example.taskweek4.ui.homefragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.repository.MovieCallBack
import com.example.taskweek4.repository.MovieRepo
import android.app.Application
import com.example.taskweek4.recyclerview.MovieAdabter

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application),MovieCallBack
{

    private val _movieLiveData : MutableLiveData<List<Movies>> by lazy { MutableLiveData<List<Movies>>() }
    private val _errorLiveData : MutableLiveData<String> by lazy { MutableLiveData<String>() }
     val movieList:MutableList<Movies> = mutableListOf()
    val movieLiveData: LiveData<List<Movies>>
        get() = _movieLiveData
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    private lateinit var movieData: List<Movies>

    var currentSpinner :String ?=null
    private var currentPage= 1
    var lastPosition:Int =0
    var page=1
    var isFirst =true
    var isFirstCreation =false
    val movieAdapter: MovieAdabter = MovieAdabter(mutableListOf())



    init {
        MovieRepo.createDatabase(application)
    }



    fun loadMovieData(spinnerData:String ,page:Int){

        if (spinnerData == currentSpinner && this::movieData.isInitialized&&page==currentPage) {
           _movieLiveData.value = movieData
            return
        }
        currentSpinner = spinnerData
        currentPage = page
        MovieRepo.getData(this,currentSpinner!!,page)
    }


    override fun failed(message: String) {
        _errorLiveData.value =message
    }


    override fun isReadyHome(movies: List<Movies>) {
        movieData = movies
        movieList.addAll(movies)
        _movieLiveData.value =movieData
    }


    fun isLoading():Boolean{
        return MovieRepo.isLoadingHome()
    }


}
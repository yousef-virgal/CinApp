package com.example.taskweek4.ui.topRatedFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskweek4.data.models.ui.objects.Movies
import com.example.taskweek4.recyclerview.TopRatedAdapter
import com.example.taskweek4.repository.MovieRepo
import com.example.taskweek4.repository.TopRatedCallBack

class TopRatedViewModel(application: Application) : AndroidViewModel(application),TopRatedCallBack {
    private val _topRatedLiveData : MutableLiveData<List<Movies>> by lazy { MutableLiveData<List<Movies>>() }
    private val _errorLiveData : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val topRatedLiveData: LiveData<List<Movies>>
        get() = _topRatedLiveData
    val errorLiveData: LiveData<String>
        get() = _errorLiveData
    private lateinit var topRated: List<Movies>
    private var currentPage= 1
    var lastPosition:Int =0
    var page=1
    val movieAdapter: TopRatedAdapter = TopRatedAdapter(mutableListOf())


    fun loadTopRatedData(page:Int){

        if ( this::topRated.isInitialized && page==currentPage) {
            _topRatedLiveData.value = topRated
            return
        }
        currentPage = page
        MovieRepo.getTopRated(this,page)
    }
    override fun isReadyTopRated(movies: List<Movies>) {
        topRated = movies
        _topRatedLiveData.value = topRated
    }

    override fun failed(message: String) {
        _errorLiveData.value =message
    }
    fun isLoading():Boolean{
        return MovieRepo.isLoadingTopRated()
    }
}
package com.example.taskweek4.ui.itemFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskweek4.data.models.ui.objects.Movies
import com.example.taskweek4.recyclerview.RecomendationsAdapter
import com.example.taskweek4.repository.ItemCallBack
import com.example.taskweek4.repository.MovieRepo

class itemViewModel(application: Application) : AndroidViewModel(application), ItemCallBack {
    private val _movieLiveData : MutableLiveData<List<Movies>> by lazy { MutableLiveData<List<Movies>>() }
    private val _errorLiveData : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val movieLiveData: LiveData<List<Movies>>
        get() = _movieLiveData
    val errorLiveData: LiveData<String>
        get() = _errorLiveData
    var page:Int = 1
    val myAdapter: RecomendationsAdapter = RecomendationsAdapter(mutableListOf())


    fun getRecomendations(page:Int,movieId:Int){

        MovieRepo.getRecomendations(this,movieId,page)
    }

    override fun isReadyRecomendations(movies: List<Movies>) {
        _movieLiveData.value = movies
    }

    override fun failed(message: String) {
        _errorLiveData.value =message
    }

    fun isLoadingRecomendations():Boolean{
        return MovieRepo.isLoadingRecomendations()
    }
}
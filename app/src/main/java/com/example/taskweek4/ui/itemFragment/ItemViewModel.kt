package com.example.taskweek4.ui.itemFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskweek4.data.models.ui.objects.Movies
import com.example.taskweek4.data.models.ui.objects.Reviews
import com.example.taskweek4.recyclerview.RecommendationsAdapter
import com.example.taskweek4.repository.ItemCallBack
import com.example.taskweek4.repository.MovieRepo

class ItemViewModel(application: Application) : AndroidViewModel(application), ItemCallBack {
    private val _movieLiveData : MutableLiveData<List<Movies>> by lazy { MutableLiveData<List<Movies>>() }
    private val _reviewLiveData : MutableLiveData<List<Reviews>> by lazy { MutableLiveData<List<Reviews>>() }
    private val _errorLiveData : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _errorLiveDataReviews : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val movieLiveData: LiveData<List<Movies>>
        get() = _movieLiveData
    val reviewLiveData: LiveData<List<Reviews>>
        get() = _reviewLiveData

    val errorLiveData: LiveData<String>
        get() = _errorLiveData
    val reviewErrorLiveData: LiveData<String>
        get() = _errorLiveDataReviews

    var page:Int = 1
    val myAdapter: RecommendationsAdapter = RecommendationsAdapter(mutableListOf())



    fun getRecommendations(page:Int, movieId:Int){

        MovieRepo.getRecommendations(this,movieId,page)
    }

    fun getReviews(movieId:Int){
        MovieRepo.getReviews(this,movieId)
    }

    override fun isReadyRecommendations(movies: List<Movies>) {
        _movieLiveData.value = movies
    }

    override fun isReadyReviews(reviews: List<Reviews>) {
        _reviewLiveData.value = reviews
    }


    override fun failed(message: String) {
        _errorLiveData.value =message
    }

    fun isLoadingRecommendations():Boolean{
        return MovieRepo.isLoadingRecommendations()
    }
}
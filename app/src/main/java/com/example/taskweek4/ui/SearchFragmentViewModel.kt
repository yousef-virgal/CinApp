package com.example.taskweek4.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.repository.MovieRepo
import com.example.taskweek4.repository.SearchCallBack

class SearchFragmentViewModel(application: Application): AndroidViewModel(application),SearchCallBack {
    private val _searchLiveData : MutableLiveData<List<Movies>> by lazy { MutableLiveData<List<Movies>>() }
    private val _errorLiveData : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val searchLiveData: LiveData<List<Movies>>
        get() = _searchLiveData
    val errorLiveData: LiveData<String>
        get() = _errorLiveData
    private lateinit var searchData: List<Movies>
    private var currentSearch:String? =null


    fun searchForData(query:String){
        if(query == currentSearch&& this::searchData.isInitialized) {
            _searchLiveData.value = searchData
            return
        }
        currentSearch = query
        MovieRepo.searchData(this,query)
    }

    override fun isReadySearch(movies: List<Movies>) {
        searchData = movies
        _searchLiveData.value =searchData
    }

    override fun failed(message: String) {
        _errorLiveData.value =message
    }


}
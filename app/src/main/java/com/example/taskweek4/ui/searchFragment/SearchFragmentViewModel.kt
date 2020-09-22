package com.example.taskweek4.ui.searchFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.recyclerview.SearchAdapter
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
    var currentSearch:String? =null
    private var currentPage= 1
    var page:Int = 1
    val searchAdapter = SearchAdapter(mutableListOf())



    fun searchForData(query:String,page:Int){
        if(query == currentSearch&& this::searchData.isInitialized&& page == currentPage) {
            _searchLiveData.value = searchData
            return
        }
        currentSearch = query
        MovieRepo.searchData(this,query,page)
    }

    override fun isReadySearch(movies: List<Movies>) {
        searchData = movies
        _searchLiveData.value =searchData
    }

    override fun failed(message: String) {
        _errorLiveData.value =message
    }

    fun isLoadingSearch():Boolean{
        return MovieRepo.isLoadingSearch()
    }


}
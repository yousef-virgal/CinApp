package com.example.taskweek4.repository

import android.content.Context
import com.example.taskweek4.data.models.database.MovieDataBase
import com.example.taskweek4.data.models.network.ApiClient
import com.example.taskweek4.data.models.network.ApiInterface
import com.example.taskweek4.data.models.remote.MovieResponse
import com.example.taskweek4.data.models.ui.Mapper
import com.example.taskweek4.data.models.ui.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MovieRepo {

    private const val apiKey:String = "1ef5bae1f03c43fc90bf2c0e1ee45480"

    private val retrofitObject = ApiClient.getRetrofit()
    private val mapper:Mapper by lazy { Mapper() }
    private val apiInterface:ApiInterface by lazy {
        retrofitObject!!.create(ApiInterface::class.java)
    }
    private var isLoading:Boolean =false
    lateinit var movieResponse: List<Movies>
    lateinit var searchResponse:List<Movies>
    private lateinit var movieDataBase: MovieDataBase


    fun getData(movieCallBack: MovieCallBack,currentMediaType:String="movie",page:Int)
    {
        isLoading =true
        if(page>1000){
            isLoading=false
            return
        }
        val call:Call<MovieResponse> = apiInterface
            .getPopularMovies(currentMediaType, "day", apiKey,page)
        call.enqueue(object:Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful) {
                    isLoading=false
                    movieDataBase.movieDao().deleteAll()
                    movieResponse = mapper.mapData(response.body()!!)
                    movieDataBase.movieDao().addMovies(movieResponse)
                    movieCallBack.isReadyHome(movieResponse)
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                t.printStackTrace()
                isLoading =false
                val message ="An Error occurred while getting the data "
                movieCallBack.failed(message)
                movieCallBack.isReadyHome(movieDataBase.movieDao().getMovies())
            }
        })

    }

    fun createDatabase(context:Context){
        movieDataBase = MovieDataBase.initializeDataBase(context)
    }

    fun isLoading():Boolean{
        return isLoading
    }

    fun searchData(movieCallBack: MovieCallBack, query:String){
        val call:Call<MovieResponse> = apiInterface.searchForMovies(apiKey,query)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    if(response.body()!!.results.isEmpty()){
                        val message ="No results found "
                        movieCallBack.failed(message)
                    }

                    searchResponse = mapper.mapData(response.body()!!)
                    movieDataBase.movieDao().addMovies(searchResponse)
                    movieCallBack.isReadyHome(searchResponse)

                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                t.printStackTrace()
                val message ="An Error occurred while getting the data "
                movieCallBack.failed(message)
                movieCallBack.isReadySearch(movieDataBase.movieDao().getMovies())
            }

        })
    }
}

interface MovieCallBack{
    fun failed(message:String)
    fun isReadyHome(movies:List<Movies>)
    fun isReadySearch(movies:List<Movies>)
}
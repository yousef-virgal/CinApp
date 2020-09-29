package com.example.taskweek4.repository

import android.content.Context
import com.example.taskweek4.data.models.database.MovieDataBase
import com.example.taskweek4.data.models.network.ApiClient
import com.example.taskweek4.data.models.network.ApiInterface
import com.example.taskweek4.data.models.remote.MovieResponse
import com.example.taskweek4.data.models.ui.mappers.Mapper
import com.example.taskweek4.data.models.ui.objects.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MovieRepo {

    private const val apiKey:String = "1ef5bae1f03c43fc90bf2c0e1ee45480"

    private val retrofitObject = ApiClient.getRetrofit()
    private val mapper: Mapper by lazy { Mapper() }
    private val apiInterface:ApiInterface by lazy {
        retrofitObject!!.create(ApiInterface::class.java)
    }
    private var isLoadingHome:Boolean =false
    private var isLoadingSearch:Boolean =false
    private var isLoadingTopRated:Boolean =false
    private var isLoadingRecomendations:Boolean =false
    lateinit var movieResponse: List<Movies>
    lateinit var searchResponse:List<Movies>
    lateinit var RecommendationsResponse:List<Movies>
    private lateinit var movieDataBase: MovieDataBase


    fun getData(movieCallBack: MovieCallBack,currentMediaType:String="movie",page:Int) {
        isLoadingHome =true
        if(page>1000){
            isLoadingHome=false
            return
        }
        val call:Call<MovieResponse> = apiInterface
            .getPopularMovies(currentMediaType, "day", apiKey,page)

        call.enqueue(object:Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful) {
                    isLoadingHome=false

                    println(response.body()!!)
                    movieResponse = mapper.mapData(response.body()!!)
                    movieDataBase.movieDao().addMovies(movieResponse)
                    movieCallBack.isReadyHome(movieResponse)
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                t.printStackTrace()
                isLoadingHome =false
                val message ="An Error occurred while getting the data "
                movieCallBack.failed(message)
                movieCallBack.isReadyHome(movieDataBase.movieDao().getMovies())
            }
        })

    }

    fun createDatabase(context:Context){
        movieDataBase = MovieDataBase.initializeDataBase(context)
    }

    fun isLoadingHome():Boolean{
        return isLoadingHome
    }
    fun isLoadingSearch():Boolean{
        return isLoadingSearch
    }
    fun isLoadingTopRated():Boolean{
        return isLoadingTopRated
    }

    fun getRecomendations(itemCallBack:ItemCallBack,movieId:Int,page:Int){
        isLoadingRecomendations =true
        val call:Call<MovieResponse> = apiInterface.getRecommendations(movieId,apiKey,page)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    isLoadingRecomendations= false
                    if(response.body()!!.results.isEmpty()) {
                        if (page == 1) {
                            val message = "No results found"
                            itemCallBack.failed(message)
                            return
                        } else {
                            val message = "No more Results"
                            itemCallBack.failed(message)
                            return
                        }
                    }
                    RecommendationsResponse = mapper.mapData(response.body()!!)
                    itemCallBack.isReadyRecomendations(RecommendationsResponse)
                    return


                    }
                }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                isLoadingRecomendations=false
                t.printStackTrace()
                val message ="An Error occurred while getting the data "
                itemCallBack.failed(message)
                itemCallBack.isReadyRecomendations(movieDataBase.movieDao().getMovies())
            }

        })

    }
    fun searchData(searchCallBack: SearchCallBack, query:String,page:Int){
        isLoadingSearch= true
        val call:Call<MovieResponse> = apiInterface.searchForMovies(apiKey,query,page)
        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    isLoadingSearch =false
                    if(response.body()!!.results.isEmpty()){
                        if(page==1) {
                            val message = "No results found"
                            searchCallBack.failed(message)
                            return
                        }
                        else {
                            val message = "No more Results"
                            searchCallBack.failed(message)
                            return
                        }
                    }
                    searchResponse = mapper.mapData(response.body()!!)
                    searchCallBack.isReadySearch(searchResponse)
                    return

                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                isLoadingSearch=false
                t.printStackTrace()
                val message ="An Error occurred while getting the data "
                searchCallBack.failed(message)
                searchCallBack.isReadySearch(movieDataBase.movieDao().getMovies())
            }

        })
    }

    fun getTopRated(topRatedCallback: TopRatedCallBack,page:Int){
        isLoadingTopRated =true
        if(page>1000){
            isLoadingTopRated=false
            return
        }
        val call:Call<MovieResponse> = apiInterface.getTopRated(apiKey,page)
        call.enqueue(object:Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful) {
                    isLoadingTopRated=false

                    movieResponse = mapper.mapData(response.body()!!)
                    movieDataBase.movieDao().addMovies(movieResponse)
                    topRatedCallback.isReadyTopRated(movieResponse)
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                t.printStackTrace()
                isLoadingTopRated=false
                val message ="An Error occurred while getting the data "
                topRatedCallback.failed(message)
                topRatedCallback.isReadyTopRated(movieDataBase.movieDao().getMovies())
            }
        })

    }
    fun getFavorites():List<Movies>{
        return movieDataBase.movieDao().getFavorites()
    }
    fun changeMovie(movie: Movies){
         movieDataBase.movieDao().changeMovie(movie)
    }
    fun isLoadingRecomendations():Boolean{
         return isLoadingRecomendations
    }
}

interface MovieCallBack{
    fun failed(message:String)
    fun isReadyHome(movies:List<Movies>)
}

interface SearchCallBack{
    fun isReadySearch(movies:List<Movies>)
    fun failed(message:String)
}

interface TopRatedCallBack {
    fun isReadyTopRated(movies:List<Movies>)
    fun failed(message: String)
}

interface ItemCallBack{
    fun isReadyRecomendations(movies:List<Movies>)
    fun failed(message:String)
}


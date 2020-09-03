package com.example.taskweek4.repository

import com.example.taskweek4.network.ApiClient
import com.example.taskweek4.network.ApiInterface
import com.example.taskweek4.network.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MovieRepo {

    private const val apiKey:String = "1ef5bae1f03c43fc90bf2c0e1ee45480"
    private val retrofitObject = ApiClient.getRetrofit()
    private val apiInterface:ApiInterface by lazy {
        retrofitObject!!.create(ApiInterface::class.java)
    }

    lateinit var movieResponse: MovieResponse

    fun getData(movieCallBack: MovieCallBack){
        if(this::movieResponse.isInitialized ) {
            return movieCallBack.isReady(movieResponse)
        }
        var call:Call<MovieResponse> = apiInterface
            .getPopulerMovies("movie", "day", apiKey)
        call.enqueue(object:Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful) {
                    movieResponse = response.body()!!
                    movieCallBack.isReady(movieResponse)
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
}

interface MovieCallBack{
    fun isReady(movieResponse: MovieResponse)
}
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
    lateinit var mediaType:String
    lateinit var movieResponse: MovieResponse

    fun getData(movieCallBack: MovieCallBack,currentMediaType:String="movie"){
        if(this::movieResponse.isInitialized &&currentMediaType == mediaType ) {
            return movieCallBack.isReady(movieResponse)
        }
        mediaType = currentMediaType
        val call:Call<MovieResponse> = apiInterface
            .getPopulerMovies(currentMediaType, "day", apiKey)
        call.enqueue(object:Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful) {
                    println(response.body())
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
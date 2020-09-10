package com.example.taskweek4.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskweek4.data.models.network.ApiClient
import com.example.taskweek4.data.models.network.ApiInterface
import com.example.taskweek4.data.models.remote.MovieObject
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

    lateinit var mediaType:String
    lateinit var movieResponse: List<Movies>





    fun getData(movieCallBack: MovieCallBack,currentMediaType:String="movie")
    {
        //val movieAns : MutableLiveData<List<Movies>> = MutableLiveData()
//        if(this::movieResponse.isInitialized &&currentMediaType == mediaType ) {
//           return
//            //movieCallBack.isReady(movieResponse)
//        }
        mediaType = currentMediaType
        val call:Call<MovieResponse> = apiInterface
            .getPopulerMovies(currentMediaType, "day", apiKey)
        call.enqueue(object:Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful) {
                    movieResponse = mapper.mapData(response.body()!!)

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
    fun isReady(movies:List<Movies>)
}
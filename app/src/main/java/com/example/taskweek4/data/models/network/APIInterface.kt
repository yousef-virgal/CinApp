package com.example.taskweek4.data.models.network

import com.example.taskweek4.data.models.remote.MovieResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface{
    @GET("trending/{media_type}/{time_window}")
    fun getPopulerMovies(
        @Path("media_type") mediaType:String,
        @Path("time_window") timeWindow:String,
        @Query("api_key") apiKey:String
    ): Call<MovieResponse>
}

object ApiClient{
    private var retrofit: Retrofit? =null
    fun getRetrofit():Retrofit?{
        if(retrofit == null)
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp3.OkHttpClient.Builder().build())
                .build()
        return retrofit
    }
}
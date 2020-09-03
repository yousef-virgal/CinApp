package com.example.taskweek4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.taskweek4.network.MovieResponse
import com.example.taskweek4.recyclerview.MovieAdabter
import com.example.taskweek4.repository.MovieCallBack
import kotlinx.android.synthetic.main.movierecyclerview.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.network.MovieObject
import com.example.taskweek4.repository.MovieRepo

class MainActivity : AppCompatActivity(),MovieCallBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movierecyclerview)
        MovieRepo.getData(this)
    }
    fun bindData(movieObjects: List<MovieObject>){
        movieRecyclerView.apply {
            adapter = MovieAdabter(movieObjects)
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        }
    }
    override fun isReady(movieResponse: MovieResponse) {
        bindData(movieResponse.results)
    }
}
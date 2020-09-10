package com.example.taskweek4.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.recyclerview.MovieAdabter
import kotlinx.android.synthetic.main.movierecyclerview.*


class MainActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movierecyclerview)

        displayData()
        movieViewModel.loadMovieData("movie")


        movieViewModel.movieLiveData.observe(
            this,
            {
                bindData(it)

            }
        )


        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                movieViewModel.loadMovieData(spinner1.selectedItem.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                movieViewModel.loadMovieData(spinner1.selectedItem.toString())
            }

        }


    }

    private fun bindData(movies: List<Movies>) {
        movieRecyclerView.apply {
            adapter = MovieAdabter(movies)
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        }

    }

    private fun displayData() {
        val list = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, resources.getStringArray(R.array.spinner2)
        )

        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner1.adapter = list
    }


}









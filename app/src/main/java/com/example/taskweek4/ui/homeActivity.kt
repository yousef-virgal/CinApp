package com.example.taskweek4.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.taskweek4.R
import com.example.taskweek4.recyclerview.MovieAdabter
import kotlinx.android.synthetic.main.mainactivity.*


class homeActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModels()
    private val movieAdabter:MovieAdabter = MovieAdabter(mutableListOf())
    private var page=1
    private var isFirst =true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        displayData()

        val navController = Navigation
            .findNavController(this, R.id.mainFragment)

        NavigationUI.setupWithNavController(navigationBar, navController)
//        setRecyclerView()
//        movieViewModel.loadMovieData("movie",page)
//
//
//        movieViewModel.movieLiveData.observe(
//            this,
//            {
//                bindData(it)
//
//            }
//        )
//
//        spinnerListener()
//        scrollListener()
//


    }

//    private fun bindData(movies: List<Movies>) {
//        movieAdabter.addData(movies)
//    }
//
//    private fun scrollListener(){
//        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                if(!movieRecyclerView.canScrollVertically(1)&&!movieViewModel.isLoading()){
//                    page++
//                    movieViewModel.loadMovieData(spinner1.selectedItem.toString(),page = page)
//                }
//            }
//
//        })
//    }
//
//
//    private fun setRecyclerView() {
//        movieRecyclerView.apply {
//            adapter = movieAdabter
//            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
//        }
//    }

    private fun displayData() {
        val list = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, resources.getStringArray(R.array.spinner2)
        )

        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner1.adapter = list
    }

//    private fun spinnerListener(){
//        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                if(isFirst) {
//                    isFirst = false
//                    return
//                }
//                page=1
//                movieAdabter.clearData()
//                setRecyclerView()
//                movieViewModel.loadMovieData(spinner1.selectedItem.toString(),page)
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//
//        }

   // }


}









package com.example.taskweek4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.recyclerview.MovieAdabter
import com.example.taskweek4.ui.MovieViewModel
import com.example.taskweek4.ui.homeActivity
import kotlinx.android.synthetic.main.fragment_homefragment.*

class Homefragment : Fragment() {
    private val movieViewModel: MovieViewModel by viewModels()
    private val movieAdabter: MovieAdabter = MovieAdabter(mutableListOf())
    private var page=1
    private var isFirst =true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_homefragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayData()
        setRecyclerView()
        spinnerListener()
        movieViewModel.loadMovieData("movie",page)
        scrollListener()

        movieViewModel.movieLiveData.observe(
            viewLifecycleOwner,
            {
                bindData(it)

            }
        )
    }

    private fun setRecyclerView() {
        movieRecyclerView.apply {
            adapter = movieAdabter
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        }
    }

    private fun bindData(movies: List<Movies>) {
        movieAdabter.addData(movies)
    }

    private fun displayData() {
        val list = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, resources.getStringArray(R.array.spinner2)
        )

        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner1.adapter = list
    }
    private fun spinnerListener(){
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(isFirst) {
                    isFirst = false
                    return
                }
                page=1
                movieAdabter.clearData()
                setRecyclerView()
                movieViewModel.loadMovieData(spinner1.selectedItem.toString(),page)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                movieViewModel.loadMovieData("movie",page)
            }

        }

    }

    private fun scrollListener(){
        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(!movieRecyclerView.canScrollVertically(1)&&!movieViewModel.isLoading()){
                    page++

                    movieViewModel.loadMovieData(spinner1.selectedItem.toString(),page = page)
                }
            }

        })
    }


}
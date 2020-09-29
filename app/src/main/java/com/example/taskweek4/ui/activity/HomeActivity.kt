package com.example.taskweek4.ui.activity


import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.taskweek4.R
import com.example.taskweek4.R.id.favToggleButton
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.ui.favouriteFragment.FavoriteViewModel
import com.example.taskweek4.ui.homefragment.HomeFragmentViewModel
import com.example.taskweek4.ui.searchFragment.SearchFragmentViewModel
import com.example.taskweek4.ui.topRatedFragment.TopRatedViewModel
import com.example.taskweek4.ui.favouriteFragment.*
import kotlinx.android.synthetic.main.design_1.*
import kotlinx.android.synthetic.main.mainactivity.*

class HomeActivity : AppCompatActivity(), MyInterface {


    private val movieViewModel: HomeFragmentViewModel by viewModels()
    private val searchViewModel: SearchFragmentViewModel by viewModels()
    private val topRatedViewModel: TopRatedViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.mainactivity)






        val navController = Navigation
            .findNavController(this, R.id.mainFragment)
        NavigationUI.setupWithNavController(navigationBar, navController)



        movieViewModel.loadMovieData("movie", movieViewModel.page)
        topRatedViewModel.loadTopRatedData(movieViewModel.page)
       // favoriteViewModel.loadFavoriteData(movieViewModel.page)



        searchTextListener()

        movieViewModel.movieLiveData.observe(
            this,
            {
                bindHomeData(it)
                println("movieViewModel, observe ,bindHomeData")
            }
        )

        topRatedViewModel.topRatedLiveData.observe(
            this,
            {
                bindTopRatedData(it)
                println("TopViewModel, observe ,bindTopRatedData")
            }
        )

        searchViewModel.searchLiveData.observe(this, {
            bindSearchData(it)

        })

        searchViewModel.errorLiveData.observe(this,
            {
                Toast.makeText(this, searchViewModel.errorLiveData.value, Toast.LENGTH_SHORT).show()
            })

        movieViewModel.errorLiveData.observe(this, {
            Toast.makeText(this, movieViewModel.errorLiveData.value, Toast.LENGTH_SHORT).show()
        })


        iconListener()

        searchText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (searchText.text.isEmpty())
                        return false
                    else {
                        searchViewModel.page = 1
                        searchViewModel.searchAdapter.clear()
                    }
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    navController.navigate(R.id.searchFragment)
                    return true
                }
                return false

            }

        })


    }

    private fun searchTextListener() {
        searchText.addTextChangedListener {

            if (searchText.text.toString().isNotEmpty()) {
                searchIcon.setImageResource(R.drawable.ic_baseline_close_24)
            } else {
                searchIcon.setImageResource(R.drawable.ic_baseline_search_24)
            }
        }
    }

    private fun iconListener() {
        searchIcon.setOnClickListener {
            if (searchText.text.toString().isNotEmpty()) {
                searchText.text.clear()
            }
        }
    }


    override fun getText(): String {
        return searchText.text.toString()
    }

    private fun bindHomeData(movies: List<Movies>) {
        movieViewModel.movieAdapter.addData(movies)
    }

    private fun bindTopRatedData(movies: List<Movies>) {
        topRatedViewModel.movieAdapter.addData(movies)
    }

    private fun bindSearchData(list: List<Movies>) {
        searchViewModel.searchAdapter.add(list)
    }


}
interface MyInterface{
    fun getText():String
}






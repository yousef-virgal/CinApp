package com.example.taskweek4.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.objects.Movies
import com.example.taskweek4.ui.homefragment.HomeFragmentViewModel
import com.example.taskweek4.ui.itemFragment.ItemViewModel
import com.example.taskweek4.ui.searchFragment.SearchFragmentViewModel
import com.example.taskweek4.ui.topRatedFragment.TopRatedViewModel
import kotlinx.android.synthetic.main.mainactivity.*

class HomeActivity : AppCompatActivity(), SearchCallbackInterface {


    private val movieViewModel: HomeFragmentViewModel by viewModels()
    private val searchViewModel: SearchFragmentViewModel by viewModels()
    private val topRatedViewModel: TopRatedViewModel by viewModels()
    private val itemViewModel: ItemViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.mainactivity)






        val navController = Navigation
            .findNavController(this, R.id.mainFragment)
        NavigationUI.setupWithNavController(navigationBar, navController)

        movieViewModel.loadMovieData("movie", movieViewModel.page)
        topRatedViewModel.loadTopRatedData(movieViewModel.page)



        searchTextListener()

        movieViewModel.movieLiveData.observe(
            this,
            {
                bindHomeData(it)

            }
        )

        movieViewModel.errorLiveData.observe(this, {
            Toast.makeText(this, movieViewModel.errorLiveData.value, Toast.LENGTH_SHORT).show()
        })


        topRatedViewModel.topRatedLiveData.observe(
            this,
            {
                bindTopRatedData(it)
            }
        )
        topRatedViewModel.errorLiveData.observe(this, {
            Toast.makeText(this, topRatedViewModel.errorLiveData.value, Toast.LENGTH_SHORT).show()
        })

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

        itemViewModel.movieLiveData.observe(this,{
            itemViewModel.myAdapter.addItems(it)
        })
        itemViewModel.errorLiveData.observe(this,{
            Toast.makeText(this,itemViewModel.errorLiveData.value.toString(),Toast.LENGTH_SHORT).show()
        })

        itemViewModel.reviewErrorLiveData.observe(this,{
            Toast.makeText(this,itemViewModel.reviewErrorLiveData.value.toString(),Toast.LENGTH_SHORT).show()
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
interface SearchCallbackInterface{
    fun getText():String
}






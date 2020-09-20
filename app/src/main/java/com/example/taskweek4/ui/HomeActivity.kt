package com.example.taskweek4.ui


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
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.recyclerview.MovieAdabter
import kotlinx.android.synthetic.main.mainactivity.*
class HomeActivity : AppCompatActivity(),HomeFragmentInterface,MyInterface {


    private val movieViewModel: HomeActivityViewModel by viewModels()
    private val movieAdabter: MovieAdabter = MovieAdabter(mutableListOf())
    private var page=1
    private var isFirst =true
    private var lastPosition:Int =0
    private lateinit var currentSpinner:String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.mainactivity)

        val navController = Navigation
            .findNavController(this, R.id.mainFragment)
        NavigationUI.setupWithNavController(navigationBar, navController)




        movieViewModel.loadMovieData("movie", page = page)


        searchTextListener()

        iconListener()

        searchText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (searchText.text.isEmpty())
                        return false
                    navController.navigate(R.id.searchFragment)
                    return true
                }
                return false

            }

        })

        movieViewModel.movieLiveData.observe(
            this,
            {
                bindHomeData(it)
            }
        )

        movieViewModel.errorLiveData.observe(this, {
            Toast.makeText(this, movieViewModel.errorLiveData.value, Toast.LENGTH_SHORT).show()
        })


    }
    private fun searchTextListener(){
        searchText.addTextChangedListener {

            if(searchText.text.toString().isNotEmpty()){
                searchIcon.setImageResource(R.drawable.ic_baseline_close_24)
            }
            else{
                searchIcon.setImageResource(R.drawable.ic_baseline_search_24)
            }
        }
    }

    private fun iconListener(){
        searchIcon.setOnClickListener {
            if(searchText.text.toString().isNotEmpty()){
                searchText.text.clear()
            }
        }
    }

    override fun getAdabter():MovieAdabter {

        return movieAdabter
    }

    override fun callMovieViewModel(text: String, page: Int) {
        movieViewModel.loadMovieData(text, page)
    }

    override fun getIsFirst(): Boolean {
        return isFirst
    }

    override fun setIsFirst(input: Boolean) {
        isFirst = input
    }

    override fun getPageForHomeFragment(): Int {
        return page
    }

    override fun setPageForHomeFragment(input: Int) {
        page= input
    }

    override fun callIsLoading():Boolean {
        return movieViewModel.isLoading()
    }

    override fun setLastPosition(input: Int) {
        lastPosition = input
    }

    override fun getLastPosition(): Int {
        return lastPosition
    }

    override fun getSpinner(): String {
        return currentSpinner
    }

    override fun setSpinner(input: String) {
        currentSpinner =input
    }


    private fun bindHomeData(movies: List<Movies>) {
        movieAdabter.addData(movies)
    }

    override fun getText(): String {
        return searchText.text.toString()
    }


}

interface HomeFragmentInterface{
    fun getAdabter():MovieAdabter
    fun callMovieViewModel(text: String, page: Int)
    fun getIsFirst():Boolean
    fun setIsFirst(input: Boolean)
    fun getPageForHomeFragment():Int
    fun setPageForHomeFragment(input: Int)
    fun callIsLoading():Boolean
    fun setLastPosition(input: Int)
    fun getLastPosition():Int
    fun getSpinner():String
    fun setSpinner(input: String)
}

interface MyInterface{
    fun getText():String
}









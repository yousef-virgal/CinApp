package com.example.taskweek4.ui


import android.content.pm.ActivityInfo
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
class HomeActivity : AppCompatActivity(),MyInterface {


    private val movieViewModel: HomeActivityViewModel by viewModels()
    private val searchViewModel: SearchFragmentViewModel by viewModels()







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.mainactivity)

        val navController = Navigation
            .findNavController(this, R.id.mainFragment)
        NavigationUI.setupWithNavController(navigationBar, navController)




        movieViewModel.loadMovieData("movie",movieViewModel.page)


        searchTextListener()

        iconListener()

        searchText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (searchText.text.isEmpty())
                        return false
                    if(searchText.text.toString()!=searchViewModel.currentSearch) {
                        searchViewModel.page = 1
                        searchViewModel.searchAdapter.clear()
                    }
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    navController.navigate(R.id.searchFragment)
                    return true
                }
                return false

            }

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


    override fun getText(): String {
        return searchText.text.toString()
    }

}

interface MyInterface{
    fun getText():String
}









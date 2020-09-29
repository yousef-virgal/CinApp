package com.example.taskweek4.ui.itemFragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.recyclerview.MovieAdapter
import com.example.taskweek4.recyclerview.RecomendationsAdapter
import com.example.taskweek4.ui.homefragment.HomeFragmentViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_item.*
import kotlinx.android.synthetic.main.fragment_search.*


class ItemFragment : Fragment() {

    lateinit var prefs: SharedPreferences
    lateinit var model: itemViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(requireActivity()).get(itemViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onStart() {
        super.onStart()

        prefs= requireContext().getSharedPreferences("DeviceToken", MODE_PRIVATE)
        Picasso.get()
            .load("http://image.tmdb.org/t/p/w500"+prefs.getString("posterPath", null) )
            .into(myImage)
        movieName.text = prefs.getString("title",null)
        releaseDate.text = prefs.getString("releaseDate",null)
        overView_text.text = prefs.getString("overView",null)
        model.getRecomendations(model.page,prefs.getInt("movieId",0))
        setRecycler()


        scrollListener()
    }


//    private fun bindData(movies:List<Movies>){
//        myAdapter.addItems(movies)
//
//    }

    private fun setRecycler(){
        similarMoviesRecylerView.apply {
            adapter =model.myAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun scrollListener(){

        similarMoviesRecylerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(!similarMoviesRecylerView.canScrollVertically(1)&&!(model.isLoadingRecomendations())) {
                    model.page = model.page.plus(1)
                    model.getRecomendations(model.page,prefs.getInt("movieId",0))
                }
            }

        })
    }

    override fun onDestroyView() {
        println("aaaaaa")
        super.onDestroyView()
        model.page=1
        model.myAdapter.clear()

    }
}

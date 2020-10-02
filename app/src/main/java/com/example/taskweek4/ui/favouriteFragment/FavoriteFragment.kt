package com.example.taskweek4.ui.favouriteFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.recyclerview.FavoritesAdapter
import kotlinx.android.synthetic.main.fragment_favourite.*

class FavoriteFragment: Fragment() {
    lateinit var favMovieAdapter: FavoritesAdapter
    private lateinit var model: FavoriteViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        model = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        favMovieAdapter = FavoritesAdapter( model.getFavourites())
        setRecyclerView()
        super.onActivityCreated(savedInstanceState)
    }

    private fun setRecyclerView() {
        favouriteRecyclerView.apply {

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter =favMovieAdapter

        }
    }
}
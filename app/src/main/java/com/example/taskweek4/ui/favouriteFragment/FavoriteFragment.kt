package com.example.taskweek4.ui.favouriteFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.objects.Movies
import com.example.taskweek4.recyclerview.FavoritesAdapter
import com.example.taskweek4.repository.MovieRepo
import kotlinx.android.synthetic.main.fragment_favourite.*

class FavoriteFragment: Fragment() {
    lateinit var model: FavoriteViewModel
    private val favMovieAdapter: FavoritesAdapter = FavoritesAdapter(MovieRepo.getFavorites() as MutableList<Movies>)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        model = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        setRecyclerView()


        model.errorLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, model.errorLiveData.value, Toast.LENGTH_SHORT).show()
        })



        super.onActivityCreated(savedInstanceState)
    }

    private fun setRecyclerView() {
        favouriteRecyclerView.apply {

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = favMovieAdapter

        }
       // movieRecyclerView.scrollToPosition(model.lastPosition)
    }
}
package com.example.taskweek4.ui.itemFragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskweek4.R
import com.example.taskweek4.data.models.remote.MovieResponse
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.repository.MovieRepo
import com.example.taskweek4.repository.MovieRepo.movieResponse
import com.example.taskweek4.ui.homefragment.HomeFragmentViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_item.*


class ItemFragment : Fragment() {

    lateinit var prefs: SharedPreferences





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





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

        favoriteToggleButton.setOnCheckedChangeListener { _, favChecked ->

            if(favChecked)
                favoriteToggleButton.setBackgroundResource(R.drawable.ic_heart_fill)
            if(!favChecked)
                favoriteToggleButton.setBackgroundResource(R.drawable.ic_heart_empty)
            MovieRepo.changeMovie(mapMovieData(prefs,favChecked))

        }






    }
    fun mapMovieData(prefs: SharedPreferences, favCheck:Boolean): Movies {
        val movie: Movies
        movie = Movies(
            prefs.getInt("movieID",1), prefs.getFloat("rate", 5F).toDouble(), prefs.getString("title",null),
            prefs.getString("releaseDate",null),prefs.getString("backdropPath",null),
            prefs.getString("posterPath",null),prefs.getString("overView",null),
            prefs.getString("mediaType",null),prefs.getLong("voteCount",1).toDouble(),
            prefs.getString("title",null),favCheck


        )
        return movie
    }

}
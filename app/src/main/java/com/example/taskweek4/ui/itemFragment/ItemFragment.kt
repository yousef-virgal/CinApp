package com.example.taskweek4.ui.itemFragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.repository.MovieRepo
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
        //SET ON CHECKED LISTNER OF FAVORITE BUTTON //
       /* favoriteToggleButton.setOnCheckedChangeListener { _, favChecked ->

            println("favChecked = $favChecked")*/

            /*if(movies[position].fav == true)
                holder.favButton.setBackgroundResource(R.drawable.ic_heart_empty)
            if(movies[position].fav == false)
                holder.favButton.setBackgroundResource(R.drawable.ic_heart_fill)*/


            /*movies[position].fav = favChecked

            if(movies[position].fav == true)
                favoriteToggleButton.setBackgroundResource(R.drawable.ic_heart_fill)
            if(movies[position].fav == false)
                favoriteToggleButton.setBackgroundResource(R.drawable.ic_heart_empty)


            // Toast.makeText(holder.itemView.context, if( favChecked) " ${holder.movieName.text}  is added to Favorites" else " ${holder.movieName.text} is removed from Favorites", Toast.LENGTH_SHORT).show()
            MovieRepo.changeMovie(movies[position])



            //favoriteViewModel.changeMovieFromViewModel(MovieRepo,movies[position])

            //  holder.favButton.isChecked = !holder.favButton.isChecked
        }

        if(movies[position].fav == true)
        {
            holder.favButton.setBackgroundResource(R.drawable.ic_heart_fill)
            holder.favButton.isChecked = true
        }
        if(movies[position].fav == false) {
            holder.favButton.setBackgroundResource(R.drawable.ic_heart_empty)
        }

*/



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



    }

}
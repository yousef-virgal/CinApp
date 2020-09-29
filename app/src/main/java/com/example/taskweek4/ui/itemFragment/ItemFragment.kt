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



    }

}
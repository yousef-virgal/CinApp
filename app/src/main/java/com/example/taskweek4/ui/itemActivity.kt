package com.example.taskweek4.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.taskweek4.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_intent.*


class itemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_intent)
        bindData()
    }

    private fun bindData(){
        name.text =intent.getStringExtra("title")
        overViewText.text = intent.getStringExtra("overViewText")
        Picasso.get().load("http://image.tmdb.org/t/p/w500" + intent.getSerializableExtra("Image")).into(
            ImagePoster
        )

    }
}
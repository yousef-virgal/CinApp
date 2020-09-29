package com.example.taskweek4.ui.activity

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.taskweek4.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_intent.*

val toggleButton: ToggleButton? = null



class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_intent)
        myToggleButton.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, if(isChecked) "Added To Favorites" else "Removed From Favorites", Toast.LENGTH_SHORT).show()
        }



        bindData()}

    private fun bindData(){
        name.text =intent.getStringExtra("title")
        overViewText.text = intent.getStringExtra("overViewText")
        Picasso.get().load("http://image.tmdb.org/t/p/w500" + intent.getSerializableExtra("Image")).into(
            ImagePoster
        )

    }
}
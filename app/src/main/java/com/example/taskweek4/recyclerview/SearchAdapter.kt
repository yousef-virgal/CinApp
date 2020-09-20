package com.example.taskweek4.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.ui.ItemActivity
import com.squareup.picasso.Picasso


class SearchAdapter(val movies:List<Movies>): RecyclerView.Adapter<MovieAdabter.NormalMovieHolder>() {

    private lateinit var intent: Intent
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdabter.NormalMovieHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.design_1,parent,false)
        return MovieAdabter.NormalMovieHolder(v)
    }

    override fun onBindViewHolder(holder: MovieAdabter.NormalMovieHolder, position: Int) {

        holder.itemView.setOnClickListener {
            intent = Intent(
                holder.itemView.context,
                ItemActivity::class.java
            )


            intent.putExtra("title", movies[position].title)
            intent.putExtra("overViewText", movies[position].overview)
            intent.putExtra("Image", movies[position].backdropPath)
            holder.itemView.context.startActivity(intent)
        }


        if (movies[position].mediaType == "movie")
            holder.movieName.text = movies[position].title
        else
            holder.movieName.text = movies[position].name
        holder.movieScore.text = movies[position].voteAverage.toString()
        holder.movieType.text = movies[position].mediaType
        Picasso.get()
            .load("http://image.tmdb.org/t/p/w500" + movies[position].posterPath)
            .into(holder.moviePoster)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}
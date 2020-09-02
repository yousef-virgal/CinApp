package com.example.taskweek4.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.network.MovieObject
import com.squareup.picasso.Picasso

class MovieAdabter(val movieObjects: List<MovieObject>): RecyclerView.Adapter<MovieAdabter.MovieHolder>() {
    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                //TODO create new page
            }
        }
        val moviePoster:ImageView =itemView.findViewById(R.id.movie_poster)
        val movieName:TextView = itemView.findViewById(R.id.movie_name)
        val movieScore:TextView = itemView.findViewById(R.id.movie_genre)
        val movieType:TextView = itemView.findViewById(R.id.movie_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.design_1,parent,false)
        return MovieHolder(v)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.movieName.text = movieObjects[position].title
        holder.movieScore.text = movieObjects[position].voteAverage.toString()
        holder.movieType.text = movieObjects[position].mediaType
        Picasso.get()
            .load("http://image.tmdb.org/t/p/w500"+movieObjects[position].posterPath)
            .into(holder.moviePoster)


    }

    override fun getItemCount(): Int {
        return movieObjects.size
    }
}
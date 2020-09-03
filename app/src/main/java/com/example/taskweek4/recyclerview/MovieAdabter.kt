package com.example.taskweek4.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.MainActivity2
import com.example.taskweek4.R
import com.example.taskweek4.network.MovieObject
import com.squareup.picasso.Picasso

class MovieAdabter(private val movieObjects: List<MovieObject>): RecyclerView.Adapter<MovieAdabter.NormalMovieHolder>() {

    private lateinit var intent: Intent
    class NormalMovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val moviePoster:ImageView = itemView.findViewById(R.id.movie_poster)
        val movieName:TextView = itemView.findViewById(R.id.movie_name)
        val movieScore:TextView = itemView.findViewById(R.id.movie_genre)
        val movieType:TextView = itemView.findViewById(R.id.movie_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalMovieHolder {
            val v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.design_1, parent, false)
            return NormalMovieHolder(v)

    }

    override fun onBindViewHolder(holder: NormalMovieHolder, position: Int) {
        holder.itemView.setOnClickListener {
            intent = Intent(holder.itemView.context,MainActivity2::class.java)


            intent.putExtra("title", movieObjects[position].title)
            intent.putExtra("overViewText",movieObjects[position].overview)
            intent.putExtra("Image",movieObjects[position].backdropPath)
            holder.itemView.context.startActivity(intent)
        }


            if (movieObjects[position].mediaType == "movie")
                holder.movieName.text = movieObjects[position].title
            else
                holder.movieName.text = movieObjects[position].name
            holder.movieScore.text = movieObjects[position].voteAverage.toString()
            holder.movieType.text = movieObjects[position].mediaType
            Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + movieObjects[position].posterPath)
                .into(holder.moviePoster)
        }

    override fun getItemCount(): Int {
        return movieObjects.size
    }
}


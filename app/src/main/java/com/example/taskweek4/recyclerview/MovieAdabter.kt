package com.example.taskweek4.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.ui.MainActivity2
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.Movies
import com.squareup.picasso.Picasso

class MovieAdabter(private val movies: List<Movies>): RecyclerView.Adapter<MovieAdabter.NormalMovieHolder>() {

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
            intent = Intent(holder.itemView.context,
                MainActivity2::class.java)


            intent.putExtra("title", movies[position].title)
            intent.putExtra("overViewText",movies[position].overview)
            intent.putExtra("Image",movies[position].backdropPath)
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


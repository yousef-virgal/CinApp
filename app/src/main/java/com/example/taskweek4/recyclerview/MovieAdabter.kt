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

class MovieAdabter(private val movies: List<Movies>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var intent: Intent
    private val NON_NORMAL_VIEW_HOLDER = 0
    private val NORMAL_VIEW_HOLDER =1
    private var myPostion:Int= 0

    class NormalMovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val moviePoster:ImageView = itemView.findViewById(R.id.movie_poster)
        val movieName:TextView = itemView.findViewById(R.id.movie_name)
        val movieScore:TextView = itemView.findViewById(R.id.movie_genre)
        val movieType:TextView = itemView.findViewById(R.id.movie_duration)
    }

    class NonNormalMovieHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val moviePoster1:ImageView = itemView.findViewById(R.id.firstPoster)
        val moviePoster2:ImageView = itemView.findViewById(R.id.secondPoster)
        val moviePoster3:ImageView = itemView.findViewById(R.id.thirdPoster)
    }

    class BlankViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            1 -> {
                if(myPostion == 1||myPostion == 2){
                    val v: View =
                        LayoutInflater.from(parent.context).inflate(R.layout.blank, parent, false)
                    return BlankViewHolder(v)
                }
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_1, parent, false)
                return NormalMovieHolder(v)
            }
            0 -> {
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_2, parent, false)
                return NonNormalMovieHolder(v)
            }
            else->{
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_1, parent, false)
                return NormalMovieHolder(v)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NormalMovieHolder && position != 1 && position != 2) {
            holder.itemView.setOnClickListener {
                intent = Intent(
                    holder.itemView.context,
                    MainActivity2::class.java
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

        else if(holder is NonNormalMovieHolder){
            Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + movies[position].posterPath)
                .into(holder.moviePoster1)
            Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + movies[position+1].posterPath)
                .into(holder.moviePoster2)
            Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + movies[position+2].posterPath)
                .into(holder.moviePoster3)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            myPostion = position
            NON_NORMAL_VIEW_HOLDER

        } else {
            myPostion = position
            NORMAL_VIEW_HOLDER
        }
    }
}


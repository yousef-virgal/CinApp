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

class MovieAdabter(private val movies: MutableList<Movies>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var intent: Intent
    private val nonNormalViewHolder = 0
    private val normalViewHolder =1
    private val loadingViewHolder =2
    private var myPosition:Int= 0

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
    class LoadingViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            nonNormalViewHolder -> {
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_2, parent, false)
                return NonNormalMovieHolder(v)
            }
            normalViewHolder -> {
                if(myPosition == 1||myPosition == 2){
                    val v: View =
                        LayoutInflater.from(parent.context).inflate(R.layout.blank, parent, false)
                    return BlankViewHolder(v)
                }
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_1, parent, false)
                return NormalMovieHolder(v)
            }
            loadingViewHolder->{
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.loading, parent, false)
                return LoadingViewHolder(v)
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

            holder.moviePoster1.setOnClickListener {
                    intent = Intent(
                        holder.itemView.context,
                        MainActivity2::class.java
                    )


                    intent.putExtra("title", movies[position].title)
                    intent.putExtra("overViewText", movies[position].overview)
                    intent.putExtra("Image", movies[position].backdropPath)
                    holder.itemView.context.startActivity(intent)

            }
            holder.moviePoster2.setOnClickListener {
                intent = Intent(
                    holder.itemView.context,
                    MainActivity2::class.java
                )


                intent.putExtra("title", movies[position+1].title)
                intent.putExtra("overViewText", movies[position+1].overview)
                intent.putExtra("Image", movies[position+1].backdropPath)
                holder.itemView.context.startActivity(intent)
            }
            holder.moviePoster3.setOnClickListener {
                intent = Intent(
                    holder.itemView.context,
                    MainActivity2::class.java
                )


                intent.putExtra("title", movies[position+2].title)
                intent.putExtra("overViewText", movies[position+2].overview)
                intent.putExtra("Image", movies[position+2].backdropPath)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun addData(list: List<Movies>){
        movies.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData(){
        movies.clear()
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> {
                myPosition = position
                nonNormalViewHolder
            }
            movies.size - 1 -> {
                loadingViewHolder
            }
            else -> {
                myPosition = position
                normalViewHolder
            }
        }

    }
}


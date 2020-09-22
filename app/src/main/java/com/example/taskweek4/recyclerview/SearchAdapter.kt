package com.example.taskweek4.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.ui.activity.ItemActivity
import com.squareup.picasso.Picasso


class SearchAdapter(val movies:MutableList<Movies>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var intent: Intent
    private val loadingViewHolder = 0
    private val normalViewHolder =1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {

            1 -> {
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_1, parent, false)
                return MovieAdabter.NormalMovieHolder(v)
            }
            0->{
               val v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.loading, parent, false)
                return MovieAdabter.LoadingViewHolder(v)
            }
            else->{
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_1, parent, false)
                return MovieAdabter.NormalMovieHolder(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieAdabter.NormalMovieHolder) {
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
            if(movies[position].posterPath != null)
                Picasso.get()
                    .load("http://image.tmdb.org/t/p/w500" + movies[position].posterPath)
                    .into(holder.moviePoster)
            else
                holder.moviePoster.setImageResource(R.drawable.questionmark)

        }

    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun add(list: List<Movies>){
        movies.addAll(list)
        notifyDataSetChanged()
    }

    fun clear(){
        movies.clear()
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            movies.size-1->loadingViewHolder
            else-> normalViewHolder
        }
    }
}
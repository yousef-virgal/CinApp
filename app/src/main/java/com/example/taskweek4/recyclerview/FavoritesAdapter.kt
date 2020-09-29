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
import com.synnapps.carouselview.CarouselView

class FavoritesAdapter(val movies:MutableList<Movies>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var intent: Intent

    private val titleViewHolder:Int =0
    private val normalViewHolder:Int =1
    private val movieText:String = "movie"

    class FavoritesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            titleViewHolder->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.favourites_title,parent,false)
                FavoritesViewHolder(v)
            }
            normalViewHolder->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.design_1,parent,false)
                MovieAdabter.NormalMovieHolder(v)
            }
            else->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_favourite,parent,false)
                MovieAdabter.NormalMovieHolder(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder is MovieAdabter.NormalMovieHolder){
            holder.itemView.setOnClickListener {
                intent = Intent(
                    holder.itemView.context,
                    ItemActivity::class.java
                )
                intent.putExtra("title", movies[position-1].title)
                intent.putExtra("overViewText", movies[position-1].overview)
                intent.putExtra("Image", movies[position-1].backdropPath)
                holder.itemView.context.startActivity(intent)
            }


            holder.movieName.text = movies[position-1].title
            holder.movieScore.text = movies[position-1].voteAverage.toString()
            holder.movieType.text = movieText
            Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + movies[position-1].posterPath)
                .into(holder.moviePoster)
        }
    }

    override fun getItemCount(): Int {
        return movies.size+1
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0->titleViewHolder
            else->normalViewHolder
        }
    }

    fun addData(list:List<Movies>){
        movies.addAll(list)
        notifyDataSetChanged()
    }
}
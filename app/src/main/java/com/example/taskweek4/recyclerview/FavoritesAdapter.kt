package com.example.taskweek4.recyclerview

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.objects.Movies
import com.squareup.picasso.Picasso

class FavoritesAdapter(val movies:MutableList<Movies>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var intent: Intent

    private val titleViewHolder:Int =0
    private val normalViewHolder:Int =1
    private val movieText:String = "movie"
    private lateinit var  pref: SharedPreferences
    private lateinit var edt: SharedPreferences.Editor

    class FavoritesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            titleViewHolder->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.favourites_title,parent,false)
                FavoritesViewHolder(v)
            }
            normalViewHolder->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.design_1,parent,false)
                MovieAdapter.NormalMovieHolder(v)
            }
            else->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_favourite,parent,false)
                MovieAdapter.NormalMovieHolder(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder is MovieAdapter.NormalMovieHolder){
            holder.itemView.setOnClickListener {
                pref = holder.itemView.context.getSharedPreferences("DeviceToken",
                    Context.MODE_PRIVATE
                )
                edt = pref.edit()
                clickListener(position-1,edt,movies)
                holder.itemView.findNavController().navigate(R.id.action_favouriteFragment_to_itemFragment)
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
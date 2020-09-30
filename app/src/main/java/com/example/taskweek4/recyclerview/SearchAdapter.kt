package com.example.taskweek4.recyclerview

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.objects.Movies
import com.example.taskweek4.repository.MovieRepo
import com.squareup.picasso.Picasso


class SearchAdapter(val movies:MutableList<Movies>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var intent: Intent
    private val loadingViewHolder = 0
    private val normalViewHolder =1
    private lateinit var  pref: SharedPreferences
    private lateinit var edt: SharedPreferences.Editor
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {

            1 -> {
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_1, parent, false)
                return MovieAdapter.NormalMovieHolder(v)
            }
            0->{
               val v: View =
                LayoutInflater.from(parent.context).inflate(R.layout.loading, parent, false)
                return MovieAdapter.LoadingViewHolder(v)
            }
            else->{
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_1, parent, false)
                return MovieAdapter.NormalMovieHolder(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieAdapter.NormalMovieHolder) {
            holder.itemView.setOnClickListener {
                pref = holder.itemView.context.getSharedPreferences("DeviceToken",
                    Context.MODE_PRIVATE
                )
                edt = pref.edit()
                clickListener(position,edt,movies)
                holder.itemView.findNavController().navigate(R.id.action_searchFragment_to_itemFragment)
            }

            holder.addButton.setOnClickListener {

                movies[position].fav = true
                MovieRepo.changeMovie(movies[position])
                Toast.makeText(holder.itemView.context,"${movies[position].title} has been added to favs", Toast.LENGTH_SHORT).show()


            }

            holder.removeButton.setOnClickListener {
                movies[position].fav = false
                MovieRepo.changeMovie(movies[position])
                Toast.makeText(holder.itemView.context,"${movies[position].title} has been removed to favs", Toast.LENGTH_SHORT).show()

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
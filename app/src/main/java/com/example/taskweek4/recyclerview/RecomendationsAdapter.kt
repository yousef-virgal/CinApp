package com.example.taskweek4.recyclerview

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.Movies
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recomendationsitem.view.*

class RecomendationsAdapter(val movies:MutableList<Movies>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val loadingViewHolder =2
    private val normalViewHolder =1
    private lateinit var  pref: SharedPreferences
    private lateinit var edt: SharedPreferences.Editor
    class itemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val image:ImageView = itemView.findViewById<ImageView>(R.id.my_Image)
        val text: TextView = itemView.findViewById<TextView>(R.id.myText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {

            1 -> {
                val v: View =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recomendationsitem, parent, false)
                return itemHolder(v)
            }
            2 -> {
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.progressbarhorizontal, parent, false)
                return MovieAdapter.LoadingViewHolder(v)
            }
            else->{
                val v: View =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.recomendationsitem, parent, false)
                return itemHolder(v)

            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is itemHolder) {
            if (movies[position].posterPath != null)
                Picasso.get()
                    .load("http://image.tmdb.org/t/p/w500" + movies[position].posterPath)
                    .into(holder.image)
            else
                holder.image.setImageResource(R.drawable.questionmark)
            if (movies[position].mediaType == "movie")
             holder.text.text = movies[position].title
            else
                holder.text.text = movies[position].name

            holder.itemView.setOnClickListener {
                pref = holder.itemView.context.getSharedPreferences("DeviceToken",
                    Context.MODE_PRIVATE
                )
                edt = pref.edit()
                clickListener(position,edt,movies)
                holder.itemView.findNavController().navigate(R.id.action_itemFragment_self)
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            movies.size - 1 -> loadingViewHolder
            else -> normalViewHolder
        }
    }

    fun addItems(list:List<Movies>){
        movies.addAll(list)
        notifyDataSetChanged()
    }
}
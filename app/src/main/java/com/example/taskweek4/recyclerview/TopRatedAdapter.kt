package com.example.taskweek4.recyclerview

import android.content.Context
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
import com.synnapps.carouselview.CarouselView


class TopRatedAdapter(val movies:MutableList<Movies>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val titleViewHolder:Int =0
    private val normalViewHolder:Int =1
    private val loadingViewHolder:Int =2
    private val carouselViewHolder:Int = 3
    private val blankViewHolder:Int = 4
    private val movieText:String = "movie"
    private lateinit var  pref: SharedPreferences
    private lateinit var edt: SharedPreferences.Editor

    class TopRatedVieHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    class CarouselViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val carousel:CarouselView = itemView.findViewById(R.id.carouselView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            titleViewHolder->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.topratedtitle,parent,false)
                TopRatedVieHolder(v)
            }
            normalViewHolder->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.design_1,parent,false)
                MovieAdapter.NormalMovieHolder(v)
            }
            loadingViewHolder->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.loading,parent,false)
                MovieAdapter.LoadingViewHolder(v)
            }
            carouselViewHolder->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.carouselview,parent,false)
                return CarouselViewHolder(v)
            }
            blankViewHolder->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.blank,parent,false)
                return MovieAdapter.BlankViewHolder(v)
            }
            else->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_toprated,parent,false)
                MovieAdapter.NormalMovieHolder(v)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is CarouselViewHolder){
            holder.carousel.pageCount =3
            holder.carousel.setImageListener { myPosition, imageView ->
                Picasso.get()
                    .load("http://image.tmdb.org/t/p/w500" + movies[myPosition].posterPath)
                    .into(imageView)
            }

            holder.carousel.setImageClickListener { myPosition ->
                pref = holder.itemView.context.getSharedPreferences("DeviceToken",
                    Context.MODE_PRIVATE
                )
                edt = pref.edit()
                clickListener(myPosition,edt,movies)
                holder.itemView.findNavController().navigate(R.id.action_topRatedFragment_to_itemFragment)
            }
        }

        else if(holder is MovieAdapter.NormalMovieHolder){
            holder.itemView.setOnClickListener {
                pref = holder.itemView.context.getSharedPreferences("DeviceToken",
                    Context.MODE_PRIVATE
                )
                edt = pref.edit()
                clickListener(position-1,edt,movies)
                holder.itemView.findNavController().navigate(R.id.action_topRatedFragment_to_itemFragment)

            }

            holder.addButton.setOnClickListener {

                movies[position-1].fav = true
                MovieRepo.changeMovie(movies[position-1])
                Toast.makeText(holder.itemView.context,"${movies[position-1].title} has been added to favs", Toast.LENGTH_SHORT).show()


            }

            holder.removeButton.setOnClickListener {
                movies[position-1].fav = false
                MovieRepo.changeMovie(movies[position-1])
                Toast.makeText(holder.itemView.context,"${movies[position-1].title} has been removed to favs", Toast.LENGTH_SHORT).show()

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
        return movies.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0->titleViewHolder
            movies.size-1->loadingViewHolder
            1->carouselViewHolder
            2,3->blankViewHolder
            else->normalViewHolder
        }
    }

    fun addData(list:List<Movies>){
        movies.addAll(list)
        notifyDataSetChanged()
    }
}
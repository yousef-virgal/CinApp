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


class TopRatedAdapter(val movies:MutableList<Movies>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var intent: Intent

    private val titleViewHolder:Int =0
    private val normalViewHolder:Int =1
    private val loadingViewHolder:Int =2
    private val carouselViewHolder:Int = 3
    private val blankViewHolder:Int = 4
    private val movieText:String = "movie"

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
                MovieAdabter.NormalMovieHolder(v)
            }
            loadingViewHolder->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.loading,parent,false)
                MovieAdabter.LoadingViewHolder(v)
            }
            carouselViewHolder->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.carouselview,parent,false)
                return CarouselViewHolder(v)
            }
            blankViewHolder->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.blank,parent,false)
                return MovieAdabter.BlankViewHolder(v)
            }
            else->{
                val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_toprated,parent,false)
                MovieAdabter.NormalMovieHolder(v)
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
                intent = Intent(
                    holder.itemView.context,
                    ItemActivity::class.java
                )
                intent.putExtra("title", movies[myPosition].title)
                intent.putExtra("overViewText", movies[myPosition].overview)
                intent.putExtra("Image", movies[myPosition].backdropPath)
                holder.itemView.context.startActivity(intent)
            }
        }

        else if(holder is MovieAdabter.NormalMovieHolder){
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
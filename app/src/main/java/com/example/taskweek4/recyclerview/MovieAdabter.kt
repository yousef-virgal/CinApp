package com.example.taskweek4.recyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.ui.activity.ItemActivity
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.repository.MovieRepo
import com.example.taskweek4.ui.favouriteFragment.FavoriteViewModel
import com.google.android.material.internal.ContextUtils.getActivity
import com.squareup.picasso.Picasso
import com.example.taskweek4.ui.homefragment.HomeFragment
import com.example.taskweek4.ui.homefragment.HomeFragmentViewModel
import kotlinx.android.synthetic.main.design_1.*

class MovieAdabter(private val movies: MutableList<Movies>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var intent: Intent
    private val nonNormalViewHolder = 0
    private val normalViewHolder =1
    private val loadingViewHolder =2
    private val blankViewHolder =3
    private var myPosition:Int= 0
    var tempCheck:Boolean = false
   //  var favoriteViewModel: FavoriteViewModel =  ViewModelProvider().get(
      //  FavoriteViewModel::class.java)


    class NormalMovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val moviePoster:ImageView = itemView.findViewById(R.id.movie_poster)
        val movieName:TextView = itemView.findViewById(R.id.movie_name)
        val movieScore:TextView = itemView.findViewById(R.id.movie_genre)
        val movieType:TextView = itemView.findViewById(R.id.movie_duration)
        val favButton:ToggleButton = itemView.findViewById(R.id.favToggleButton)

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

                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.design_1, parent, false)
                return NormalMovieHolder(v)
            }
            blankViewHolder->{
                val v: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.blank, parent, false)
                return BlankViewHolder(v)

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


        if (holder is NormalMovieHolder ) {
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




            holder.favButton.setOnCheckedChangeListener { _, favChecked ->

                println("favChecked = $favChecked")

                /*if(movies[position].fav == true)
                    holder.favButton.setBackgroundResource(R.drawable.ic_heart_empty)
                if(movies[position].fav == false)
                    holder.favButton.setBackgroundResource(R.drawable.ic_heart_fill)*/


                movies[position].fav = favChecked

                if(movies[position].fav == true)
                    holder.favButton.setBackgroundResource(R.drawable.ic_heart_fill)
                if(movies[position].fav == false)
                    holder.favButton.setBackgroundResource(R.drawable.ic_heart_empty)


               // Toast.makeText(holder.itemView.context, if( favChecked) " ${holder.movieName.text}  is added to Favorites" else " ${holder.movieName.text} is removed from Favorites", Toast.LENGTH_SHORT).show()
                MovieRepo.changeMovie(movies[position])



               //favoriteViewModel.changeMovieFromViewModel(MovieRepo,movies[position])

              //  holder.favButton.isChecked = !holder.favButton.isChecked
            }

            if(movies[position].fav == true)
            {
                holder.favButton.setBackgroundResource(R.drawable.ic_heart_fill)
                holder.favButton.isChecked = true
            }
            if(movies[position].fav == false) {
                holder.favButton.setBackgroundResource(R.drawable.ic_heart_empty)
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
                        ItemActivity::class.java
                    )


                    intent.putExtra("title", movies[position].title)
                    intent.putExtra("overViewText", movies[position].overview)
                    intent.putExtra("Image", movies[position].backdropPath)
                    holder.itemView.context.startActivity(intent)

            }
            holder.moviePoster2.setOnClickListener {
                intent = Intent(
                    holder.itemView.context,
                    ItemActivity::class.java
                )


                intent.putExtra("title", movies[position+1].title)
                intent.putExtra("overViewText", movies[position+1].overview)
                intent.putExtra("Image", movies[position+1].backdropPath)
                holder.itemView.context.startActivity(intent)
            }
            holder.moviePoster3.setOnClickListener {
                intent = Intent(
                    holder.itemView.context,
                    ItemActivity::class.java
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
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
            1,2->{
                blankViewHolder
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


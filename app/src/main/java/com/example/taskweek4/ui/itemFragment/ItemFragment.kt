package com.example.taskweek4.ui.itemFragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.objects.Movies
import com.example.taskweek4.data.models.ui.objects.Reviews
import com.example.taskweek4.recyclerview.RecomendationsAdapter
import com.example.taskweek4.recyclerview.ReviewAdapter
import com.example.taskweek4.repository.MovieRepo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_item.*


class ItemFragment : Fragment() {


    lateinit var model: itemViewModel
    lateinit var prefs: SharedPreferences




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(requireActivity()).get(itemViewModel::class.java)
        prefs= requireContext().getSharedPreferences("DeviceToken", MODE_PRIVATE)
        setRecycler()
        model.getRecomendations(model.page,prefs.getInt("movieId",1))
        model.getReviews(prefs.getInt("movieId",1))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onStart() {
        super.onStart()


        Picasso.get()
            .load("http://image.tmdb.org/t/p/w500"+prefs.getString("backdropPath", null) )
            .into(myImage)
        movieName.text = prefs.getString("title",null)
        releaseDate.text = prefs.getString("releaseDate",null)
        overView_text.text = prefs.getString("overView",null)
        ratingBar.rating = prefs.getFloat("rate", 0.0F)/2



        model.movieLiveData.observe(this,{
            model.myAdapter.addItems(it)
        })
        model.errorLiveData.observe(this,{
            Toast.makeText(requireContext(),model.errorLiveData.value.toString(),Toast.LENGTH_SHORT).show()
        })

        model.reviewLiveData.observe(this,{
            setReviewRecycler(it)
        })
        model.reviwErrorLiveData.observe(this,{
            Toast.makeText(requireContext(),model.reviwErrorLiveData.value.toString(),Toast.LENGTH_SHORT).show()
        })
        addButton.setOnClickListener {
            MovieRepo.changeMovie(mapMovieData(prefs,true))
            Toast.makeText(requireContext(),"${prefs.getString("title",null)} has been added to favs", Toast.LENGTH_SHORT).show()
        }
        removeButton.setOnClickListener {
            MovieRepo.changeMovie(mapMovieData(prefs,false))
            Toast.makeText(requireContext(),"${prefs.getString("title",null)} has been removed from favs", Toast.LENGTH_SHORT).show()
        }


        scrollListener()
    }


    private fun setRecycler(){
        similarMoviesRecylerView.apply {

            adapter =model.myAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }
    private fun setReviewRecycler(list:List<Reviews>){
        reviewMoviesRecylerView.apply {

            adapter =ReviewAdapter(list)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun scrollListener(){

        similarMoviesRecylerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(!similarMoviesRecylerView.canScrollVertically(1)&&!(model.isLoadingRecomendations())) {
                    model.page = model.page.plus(1)
                    model.getRecomendations(model.page,prefs.getInt("movieId",0))
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.page=1
        model.myAdapter.clear()
    }

    private fun mapMovieData(prefs: SharedPreferences, favCheck:Boolean): Movies {
        return  Movies(
            prefs.getInt("movieID",1), prefs.getFloat("rate", 0.0F).toDouble(), prefs.getString("title",null),
            prefs.getString("releaseDate",null),prefs.getString("backdropPath",null),
            prefs.getString("posterPath",null),prefs.getString("overView",null),
            prefs.getString("mediaType",null),prefs.getFloat("voteCount", 1F).toDouble(),
            prefs.getString("name",null),favCheck
        )
    }
}

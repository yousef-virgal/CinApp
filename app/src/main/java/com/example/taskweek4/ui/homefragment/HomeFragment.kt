package com.example.taskweek4.ui.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import kotlinx.android.synthetic.main.fragment_homefragment.*

class HomeFragment : Fragment() {

    lateinit var model: HomeFragmentViewModel
    lateinit var linearLayout :LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_homefragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)
        setRecyclerView()
        scrollListener()

    }
    private fun scrollListener(){
        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                model.lastPosition = linearLayout.findFirstVisibleItemPosition()
                if(!movieRecyclerView.canScrollVertically(1)&&!(model.isLoading())){
                    model.page +=1
                    model.loadMovieData("movie",model.page)
                }
            }

        })
    }


    private fun setRecyclerView() {
        movieRecyclerView.apply {
            adapter=model.movieAdapter
            linearLayout =  LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            layoutManager = linearLayout

        }
        movieRecyclerView.scrollToPosition(model.lastPosition)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        model.isFirstCreation= true
    }

}

package com.example.taskweek4.ui.topRatedFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import kotlinx.android.synthetic.main.fragment_homefragment.*


class TopRatedFragment : Fragment() {
    lateinit var model: TopRatedViewModel
    private lateinit var linearLayout:LinearLayoutManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_toprated, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        model = ViewModelProvider(requireActivity()).get(TopRatedViewModel::class.java)
        setRecyclerView()




        scrollListener()

        super.onActivityCreated(savedInstanceState)
    }

    private fun setRecyclerView() {
        movieRecyclerView.apply {
            adapter = model.movieAdapter
            linearLayout =  LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            layoutManager = linearLayout
        }
        movieRecyclerView.scrollToPosition(model.lastPosition)
    }


    private fun scrollListener(){
        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                model.lastPosition = linearLayout.findFirstVisibleItemPosition()
                if(!movieRecyclerView.canScrollVertically(1)&&!(model.isLoading())){
                    model.page +=1
                    model.loadTopRatedData(model.page)
                }
            }

        })
    }

}
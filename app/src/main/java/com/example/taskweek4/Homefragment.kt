package com.example.taskweek4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.recyclerview.MovieAdabter
import com.example.taskweek4.ui.HomeActivityViewModel
import kotlinx.android.synthetic.main.fragment_homefragment.*

class Homefragment : Fragment() {

    lateinit var model:HomeActivityViewModel
    private val linearLayout = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    private val movieAdabter: MovieAdabter = MovieAdabter(mutableListOf())



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_homefragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(requireActivity()).get(HomeActivityViewModel::class.java)

        displayData()

        setRecyclerView()
        spinnerListener()

        model.movieLiveData.observe(
            requireActivity(),
            {
                bindHomeData(it)
            }
        )

        model.errorLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, model.errorLiveData.value, Toast.LENGTH_SHORT).show()
        })


        scrollListener()

    }





    private fun displayData() {
        val list = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, resources.getStringArray(R.array.spinner2)
        )

        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner1.adapter = list

    }



    private fun spinnerListener(){
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(model.isFirst) {
                    model.isFirst = false
                    return
                }
                if(model.isFirstCreation) {
                    return
                }
                model.page=1
                movieAdabter.clearData()
                model.loadMovieData(spinner1.selectedItem.toString(),model.page)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

    }


    private fun scrollListener(){
        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                model.lastPosition = linearLayout.findFirstVisibleItemPosition()
                if(!movieRecyclerView.canScrollVertically(1)&&!(model.isLoading())){
                    model.page +=1
                    model.loadMovieData(spinner1.selectedItem.toString(),model.page)
                }
            }

        })
    }


    private fun setRecyclerView() {
        movieRecyclerView.apply {
                adapter = movieAdabter
                layoutManager = linearLayout
        }
        movieRecyclerView.scrollToPosition(model.lastPosition)
    }

    private fun bindHomeData(movies: List<Movies>) {
        movieAdabter.addData(movies)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        model.isFirstCreation= true
    }

}
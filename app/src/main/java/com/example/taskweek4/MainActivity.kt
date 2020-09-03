package com.example.taskweek4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.taskweek4.network.MovieResponse
import com.example.taskweek4.recyclerview.MovieAdabter
import com.example.taskweek4.repository.MovieCallBack
import kotlinx.android.synthetic.main.movierecyclerview.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.network.MovieObject
import com.example.taskweek4.repository.MovieRepo

class MainActivity : AppCompatActivity(),MovieCallBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movierecyclerview)
        val list = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,resources.getStringArray(R.array.spinner2))
        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = list
        displayData()
    }
    private fun bindData(movieObjects: List<MovieObject>){
        movieRecyclerView.apply {
            adapter = MovieAdabter(movieObjects)
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        }

    }
    override fun isReady(movieResponse: MovieResponse) {
        bindData(movieResponse.results)
    }
    fun displayData(){
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                MovieRepo.getData(this@MainActivity,spinner1.selectedItem.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                MovieRepo.getData(this@MainActivity)
            }

        }

    }
}
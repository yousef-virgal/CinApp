package com.example.taskweek4.ui.searchFragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.ui.activity.MyInterface
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private lateinit var model: SearchFragmentViewModel
    private lateinit var myInterface: MyInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model = ViewModelProvider(requireActivity()).get(SearchFragmentViewModel::class.java)
        myInterface = context as MyInterface

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        model.searchForData(myInterface.getText(), model.page)

        setRecyclerView()
        scrollListener()


        super.onViewCreated(view, savedInstanceState)


    }


    private fun setRecyclerView(){
        searchRecyclerView.apply {
            adapter = model.searchAdapter
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        }
    }

    private fun scrollListener(){

        searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(!searchRecyclerView.canScrollVertically(1)&&!(model.isLoadingSearch())) {
                    model.page = model.page.plus(1)
                    model.searchForData(myInterface.getText(), model.page)
                }
            }

        })
    }

    override fun onDestroyView() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_USER
        super.onDestroyView()
    }

}
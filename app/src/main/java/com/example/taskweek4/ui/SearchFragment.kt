package com.example.taskweek4.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.Movies
import com.example.taskweek4.recyclerview.SearchAdapter
import kotlinx.android.synthetic.main.fragment_homefragment.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private val searchViewModel: SearchFragmentViewModel by viewModels()
    lateinit var myInterface:MyInterface
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myInterface = context as MyInterface

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchViewModel.searchForData(myInterface.getText())

        searchViewModel.errorLiveData.observe(viewLifecycleOwner,
            {
                Toast.makeText(requireContext(),searchViewModel.errorLiveData.value, Toast.LENGTH_SHORT).show()
            })

        searchViewModel.searchLiveData.observe(viewLifecycleOwner,  {
            setRecyclerView(it)
        })


        super.onViewCreated(view, savedInstanceState)


    }



    private fun setRecyclerView(movies:List<Movies>){
        searchRecyclerView.apply {
            adapter = SearchAdapter(movies)
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        }
    }


}
package com.example.taskweek4

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.ui.MyInterface
import kotlinx.android.synthetic.main.fragment_homefragment.*

class Homefragment : Fragment() {

    private lateinit var myInterface: MyInterface
    private var linearLayout=
        LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)






    override fun onAttach(context: Context) {
        super.onAttach(context)
        myInterface = context as MyInterface
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_homefragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayData()

        setRecyclerView()
        //spinnerListener()
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



//    private fun spinnerListener(){
//        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                if(myInterface.getIsFirst()) {
//                    myInterface.setIsFirst(false)
//                    myInterface.setSpinner(spinner1.selectedItem.toString())
//                    return
//                }
//                if(myInterface.getSpinner()!="movie"&&spinner1.selectedItem.toString()=="movie")
//                    return
//                myInterface.setSpinner(spinner1.selectedItem.toString())
//                println(myInterface.getSpinner())
//                myInterface.setPage(1)
//                myInterface.getAdabter().clearData()
//                myInterface.callMovieViewModel(spinner1.selectedItem.toString(),myInterface.getPage())
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//
//        }
//
//    }




    private fun scrollListener(){
        movieRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                myInterface.setLastPosition(linearLayout.findFirstVisibleItemPosition()+1)
                if(!movieRecyclerView.canScrollVertically(1)&&!(myInterface.callIsLoading())){
                    var page = myInterface.getPageForHomeFragment()
                    page++
                    myInterface.setPageForHomeFragment(page)
                    myInterface.callMovieViewModel(spinner1.selectedItem.toString(),myInterface.getPageForHomeFragment())
                }
            }

        })
    }


    private fun setRecyclerView() {
        movieRecyclerView.apply {

                adapter = myInterface.getAdabter()
                layoutManager = linearLayout

        }
        movieRecyclerView.scrollToPosition(myInterface.getLastPosition())


    }

}
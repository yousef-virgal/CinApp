package com.example.taskweek4.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskweek4.R
import com.example.taskweek4.data.models.ui.objects.Reviews

class ReviewAdapter(val list:List<Reviews>): RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val author: TextView = itemView.findViewById(R.id.reviwer)
        val content: TextView = itemView.findViewById(R.id.content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.reviewitem,parent,false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.author.text = list[position].authorName
        holder.content.text = list[position].contentText
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
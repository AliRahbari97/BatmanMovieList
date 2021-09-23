package com.example.kotlinvolleytest1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.batmanmovieslist.R

internal class Rating_Adapter (private var ratingList: List<Rating_DataModel>) : RecyclerView.Adapter<Rating_Adapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var source: AppCompatTextView = view.findViewById(R.id.rating_row_source_txtvw)
        var rate: AppCompatTextView = view.findViewById(R.id.rating_row_rate_txtvw)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.rating_rowitem, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rating = ratingList[position]
        holder.source.text = rating.source
        holder.rate.text = rating.rate


    }
    override fun getItemCount(): Int {
        return ratingList.size
    }
}
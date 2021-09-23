package com.example.kotlinvolleytest1

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.batmanmovieslist.Activity.MovieDetails_Activity
import com.example.batmanmovieslist.R

internal class Movie_Adapter(private var moviesList: List<Movie_DataModel>, var context : Context) : RecyclerView.Adapter<Movie_Adapter.MyViewHolder>() {
    
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: AppCompatTextView = view.findViewById(R.id.textViewUserName)
        var year: AppCompatTextView = view.findViewById(R.id.textViewUserEmail)
        var image: ImageView = view.findViewById(R.id.imageViewAvatar)
        val card:ConstraintLayout = view.findViewById(R.id.container)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_rowitem, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.title.text = movie.title
//        holder.genre.text = movie.year
        holder.year.text = movie.year

        Glide.with(context)
            .load(movie.image)
            .into(holder.image)

        holder.card.setOnClickListener { v ->
            val intent = Intent(v.context, MovieDetails_Activity::class.java).apply {
                putExtra("id", movie.id)
            }

            v.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return moviesList.size
    }
}
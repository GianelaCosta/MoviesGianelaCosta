package com.example.movies.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.base.BaseViewHolder
import com.example.movies.data.model.Movie
import kotlinx.android.synthetic.main.movies_item.view.*

class MainAdapter(
    private val context: Context, private val moviesList: List<Movie>,
    private val itemClickListener: OnMovieClickListener
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.movies_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(moviesList[position], position)
        }
    }

    inner class MainViewHolder(itemView: View) : BaseViewHolder<Movie>(itemView) {
        override fun bind(item: Movie, position: Int) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + item.imagePath)
                .centerCrop().into(itemView.img_movie)
            itemView.txt_movie_name.text = item.name
            itemView.txt_movie_rate.text = item.rate.toString()
            itemView.txt_movie_year.text = item.releaseDate.subSequence(0, 4)
            itemView.setOnClickListener { itemClickListener.onMovieClick(item) }
        }
    }
}

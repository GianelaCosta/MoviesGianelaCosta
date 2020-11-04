package com.example.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.data.model.Movie
import com.example.movies.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let{
            movie = it.getParcelable<Movie>("movie")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movie.backdropPath).centerCrop().into(img_movie_backdrop)
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movie.imagePath).fitCenter().into(img_movie)
        movie_title.text = movie.name
        movie_popularity.text = movie.popularity + " people watching"
        movie_ratingNo.text = movie.rate.toString()
        movie_rating.rating = movie.rate/2
        movie_detail.text = movie.description
        btn_show_reviews.setOnClickListener{
            val bundle = Bundle()
            bundle.putParcelable("movie", movie)
            findNavController().navigate(R.id.action_detailFragment_to_revirewFragment ,bundle)
        }
    }

}
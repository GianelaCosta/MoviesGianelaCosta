package com.example.movies.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieEntity
import com.example.movies.ui.viewModel.DetailViewModel
import com.example.movies.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel by viewModels<DetailViewModel>()
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
        fetchFromServer(movie.id)
    }

    private fun fetchFromServer(movieId: Int) {
        viewModel.fetchMovieDetail(movieId).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBarMovieDetail.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    downloadMovieDetail(result.data)
                    displayFromLocal(movieId)
                }
                is Resource.Failure -> {
                    if (!verifyAvailableNetwork(requireActivity() as AppCompatActivity)) {
                        manageResourceFailure(getString(R.string.no_internet_connection_message))
                        displayFromLocal(movieId)
                    } else
                        manageResourceFailure(getString(R.string.error_message, result.exception))
                }
            }
        })
    }

    private fun downloadMovieDetail(movie: Movie) {
        viewModel.saveMovieDetail(
            MovieEntity(
                movie.id,
                movie.backdropPath,
                movie.imagePath,
                movie.name,
                movie.description,
                movie.rate,
                movie.popularity,
                movie.releaseDate,
                movie.genres
            )
        )
    }

    private fun displayFromLocal(movieId: Int) {
        viewModel.fetchDownloadedMovieDetail(movieId)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        progressBarMovieDetail.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        val movie =
                            Movie(
                                result.data.id,
                                result.data.backdropPath,
                                result.data.imagePath,
                                result.data.name,
                                result.data.description,
                                result.data.rate,
                                result.data.popularity,
                                result.data.releaseDate,
                                result.data.genres
                            )
                        displayContent(movie)
                    }
                    is Resource.Failure -> {
                        manageResourceFailure(getString(R.string.error_message, result.exception))
                    }
                }
            })
    }

    private fun verifyAvailableNetwork(activity: AppCompatActivity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun manageResourceFailure(message: String) {
        progressBarMovieDetail.visibility = View.GONE
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun displayContent(movieToDisplay: Movie) {
        progressBarMovieDetail.visibility = View.GONE
        Glide.with(this).load(getString(R.string.image_base_url, movieToDisplay.backdropPath))
            .centerCrop()
            .into(img_movie_backdrop)
        Glide.with(this).load(getString(R.string.image_base_url, movieToDisplay.imagePath))
            .fitCenter()
            .into(img_movie)
        movie_title.text = movieToDisplay.name
        movie_popularity.text = getString(R.string.popularity_text, movieToDisplay.popularity)
        var genresText = ""
        for (genre in movieToDisplay.genres!!) {
            genresText += " " + genre.genreName
        }
        movie_genres.text = genresText
        movie_ratingNo.text = movieToDisplay.rate.toString()
        movie_rating.rating = movieToDisplay.rate / 2
        movie_detail.text = movieToDisplay.description

        btn_show_reviews.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("movie", movieToDisplay)
            findNavController().navigate(R.id.action_detailFragment_to_revirewFragment, bundle)
        }
    }

}
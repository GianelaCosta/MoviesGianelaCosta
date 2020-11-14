package com.example.movies.ui

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieEntity
import com.example.movies.ui.MainAdapter.OnMovieClickListener
import com.example.movies.ui.viewModel.MainViewModel
import com.example.movies.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(), OnMovieClickListener {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.deleteExpiredDownloadedMovies()
        fetchFromServer()
        displayFromLocal()
    }

    private fun setupRecyclerView() {
        rv_movies.setHasFixedSize(true)
        rv_movies.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
    }

    private fun fetchFromServer() {
        viewModel.fetchMoviesList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    downloadMoviesList(result.data)
                }
                is Resource.Failure -> {
                    if (!verifyAvailableNetwork(requireActivity() as AppCompatActivity)) {
                        manageResourceFailure(getString(R.string.no_internet_connection_message))
                    } else
                        manageResourceFailure(getString(R.string.reviews_label, result.exception))
                }
            }
        })
    }

    private fun displayFromLocal() {
        viewModel.fetchDownloadedMoviesList().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    val list = result.data.map {
                        Movie(
                            it.id,
                            it.backdropPath,
                            it.imagePath,
                            it.name,
                            it.description,
                            it.rate,
                            it.popularity,
                            it.releaseDate,
                            listOf()
                        )
                    }
                    rv_movies.adapter = MainAdapter(requireContext(), list, this)
                }
                is Resource.Failure -> {
                    manageResourceFailure(getString(R.string.reviews_label, result.exception))
                }
            }
        })
    }

    private fun manageResourceFailure(message: String) {
        progressBar.visibility = View.GONE
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun downloadMoviesList(moviesList: List<Movie>) {
        for (movie in moviesList) {
            viewModel.saveMovie(
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
    }

    override fun onMovieClick(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
    }
}
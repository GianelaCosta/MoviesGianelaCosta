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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.data.model.Movie
import com.example.movies.data.model.Review
import com.example.movies.data.model.ReviewEntity
import com.example.movies.ui.viewModel.ReviewViewModel
import com.example.movies.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_review.*

@AndroidEntryPoint
class ReviewFragment : Fragment() {

    private val viewModel by viewModels<ReviewViewModel>()
    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            movie = it.getParcelable<Movie>("movie")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.fetchReviewsList(movie.id).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    downloadMovieReviews(result.data)
                    progressBar.visibility = View.GONE
                    Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movie.imagePath)
                        .fitCenter().into(img_movie)
                    txt_reviews_found.text =
                        getString(R.string.reviews_label, result.data.size.toString())
                    rv_reviews.adapter = ReviewAdapter(requireContext(), result.data)
                }
                is Resource.Failure -> {
                    if (!verifyAvailableNetwork(requireActivity() as AppCompatActivity)) {
                        displayFromLocal()
                        manageResourceFailure("You don't have internet connection, showing saved data form your last section")
                    } else
                        manageResourceFailure("An error occurred while loading data ${result.exception}")
                }
            }
        })
    }

    private fun setupRecyclerView() {
        rv_reviews.layoutManager = LinearLayoutManager(requireContext())
        rv_reviews.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun manageResourceFailure(message: String) {
        progressBar.visibility = View.GONE
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun displayFromLocal() {
        // display room content
        viewModel.fetchDownloadedReviewsList(movie.id)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movie.imagePath)
                            .fitCenter().into(img_movie)
                        txt_reviews_found.text =
                            getString(R.string.reviews_label, result.data.size.toString())
                        val list = result.data.map { it ->
                            Review(
                                it.id,
                                it.reviewer,
                                it.reviewContent
                            )
                        }
                        rv_reviews.adapter = ReviewAdapter(requireContext(), list)
                    }
                    is Resource.Failure -> {
                        manageResourceFailure("An error occurred while loading data ${result.exception}")
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

    private fun downloadMovieReviews(reviewsList: List<Review>) {
        viewModel.saveMovieReviews(reviewsList.map {
            ReviewEntity(
                it.id,
                movie.id,
                it.reviewer,
                it.reviewContent
            )
        })
    }

}
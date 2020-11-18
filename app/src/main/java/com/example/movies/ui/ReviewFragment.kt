package com.example.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        fetchFromServer(movie.id)
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

    private fun fetchFromServer(movieId: Int) {
        viewModel.fetchReviewsList(movieId).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    downloadMovieReviews(result.data)
                    displayFromLocal(movieId)
                }
                is Resource.Failure -> {
                    progressBar.visibility = View.GONE
                    if (!verifyAvailableNetwork(requireActivity() as AppCompatActivity)) {
                        displayFromLocal(movieId)
                    } else
                        manageResourceFailure(
                            getString(R.string.error_message, result.exception),
                            requireContext()
                        )
                }
            }
        })
    }


    private fun displayFromLocal(movieId: Int) {
        viewModel.fetchDownloadedReviewsList(movieId)
            .observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        Glide.with(this).load(getString(R.string.image_base_url, movie.imagePath))
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
                        progressBar.visibility = View.GONE
                        manageResourceFailure(
                            getString(R.string.error_message, result.exception),
                            requireContext()
                        )
                    }
                }
            })
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
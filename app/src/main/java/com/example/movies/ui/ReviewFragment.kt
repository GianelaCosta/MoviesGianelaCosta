package com.example.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.data.model.Movie
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
        requireArguments().let{
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
        viewModel.fetchReviewsList(movie.id).observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    Glide.with(this).load("https://image.tmdb.org/t/p/w500" + movie.imagePath).fitCenter().into(img_movie)
                    txt_reviews_found.text = "reviews(" + result.data.size + ")"
                    rv_reviews.adapter = ReviewAdapter(requireContext(), result.data)
                }
                is Resource.Failure -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "An error occurred while loading data ${result.exception}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setupRecyclerView(){
        rv_reviews.layoutManager = LinearLayoutManager(requireContext())
        rv_reviews.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
    }
}
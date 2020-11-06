package com.example.movies.ui.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.ReviewEntity
import com.example.movies.domain.Repo
import com.example.movies.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReviewViewModel @ViewModelInject constructor(private val repo: Repo) : ViewModel() {

    fun fetchReviewsList(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getReviewsList(movieId))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun saveMovieReviews(reviews: List<ReviewEntity>) {
        viewModelScope.launch {
            repo.insertReviews(reviews)
        }
    }

    fun fetchDownloadedReviewsList(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getSavedReviewsList(movieId))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}
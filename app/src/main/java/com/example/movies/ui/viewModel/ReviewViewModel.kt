package com.example.movies.ui.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movies.domain.Repo
import com.example.movies.vo.Resource
import kotlinx.coroutines.Dispatchers

class ReviewViewModel @ViewModelInject constructor(private val repo: Repo) : ViewModel() {

    fun fetchReviewsList(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getReviewsList(movieId))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}
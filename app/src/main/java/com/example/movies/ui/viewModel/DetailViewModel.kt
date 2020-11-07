package com.example.movies.ui.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.MovieEntity
import com.example.movies.domain.Repo
import com.example.movies.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel @ViewModelInject constructor(private val repo: Repo) : ViewModel() {

    fun fetchMovieDetail(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getMovieDetail(movieId))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun saveMovieDetail(movie: MovieEntity) {
        viewModelScope.launch {
            repo.insertMovieDetail(movie)
        }
    }

    fun fetchDownloadedMovieDetail(movieId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getSavedMovieDetail(movieId))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

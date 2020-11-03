package com.example.movies.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.MovieEntity
import com.example.movies.domain.Repo
import com.example.movies.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repo): ViewModel(){

    val fetchMoviesList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
           emit(repo.getMoviesList())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun saveMovie(movie: MovieEntity) {
        viewModelScope.launch {
            repo.insertMovie(movie)
        }
    }

    fun fetchDownloadedMoviesList() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getSavedMoviesList())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}
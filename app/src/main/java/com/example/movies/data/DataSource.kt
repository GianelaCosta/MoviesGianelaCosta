package com.example.movies.data

import com.example.movies.AppDatabase
import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.model.Review
import com.example.movies.vo.Resource
import com.example.movies.vo.RetrofitClient

class DataSource(private val appDatabase: AppDatabase) {

    suspend fun getMovies(): Resource<List<Movie>>{
        return Resource.Success(RetrofitClient.webservice.getMovies().moviesList)
    }

    suspend fun getReviews(movieId: Int): Resource<List<Review>>{
        return Resource.Success(RetrofitClient.webservice.getReviews(movieId).reviewsList)
    }

    suspend fun getSavedMovies(): List<MovieEntity>{
        return appDatabase.movieDao().getAllMovies()
    }

    suspend fun insertMovieRoom(movie: MovieEntity){
        appDatabase.movieDao().insertMovie(movie)
    }
}
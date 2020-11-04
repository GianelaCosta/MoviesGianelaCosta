package com.example.movies.data

import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.model.Review
import com.example.movies.domain.DataSource
import com.example.movies.domain.MovieDao
import com.example.movies.vo.Resource
import com.example.movies.vo.RetrofitClient
import javax.inject.Inject

class DataSourceImp @Inject constructor(private val movieDao: MovieDao) : DataSource {

    override suspend fun getMovies(): Resource<List<Movie>> {
        return Resource.Success(RetrofitClient.webservice.getMovies().moviesList)
    }

    override suspend fun getReviews(movieId: String): Resource<List<Review>> {
        return Resource.Success(RetrofitClient.webservice.getReviews(movieId).reviewsList)
    }

    override suspend fun getSavedMovies(): Resource.Success<List<MovieEntity>> {
        return Resource.Success(movieDao.getAllMovies())
    }

    override suspend fun insertMovieRoom(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }
}
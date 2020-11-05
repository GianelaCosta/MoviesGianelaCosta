package com.example.movies.domain

import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.model.Review
import com.example.movies.vo.Resource

interface DataSource {
    suspend fun getMovies(): Resource<List<Movie>>
    suspend fun getReviews(movieId: Int): Resource<List<Review>>
    suspend fun getSavedMovies(): Resource.Success<List<MovieEntity>>
    suspend fun insertMovieRoom(movie: MovieEntity)
    suspend fun deleteMovieRoom(movie: MovieEntity)
}
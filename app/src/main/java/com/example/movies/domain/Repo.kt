package com.example.movies.domain

import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.model.Review
import com.example.movies.vo.Resource

interface Repo {
    suspend fun getMoviesList():Resource<List<Movie>>
    suspend fun getReviewsList(movieId: Int):Resource<List<Review>>
    suspend fun getSavedMoviesList():List<MovieEntity>
    suspend fun insertMovie(movie: MovieEntity)
}
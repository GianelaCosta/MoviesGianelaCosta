package com.example.movies.domain

import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.model.Review
import com.example.movies.data.model.ReviewEntity
import com.example.movies.vo.Resource

interface Repo {
    suspend fun getMoviesList(): Resource<List<Movie>>
    suspend fun getMovieDetail(movieId: Int): Resource<Movie>
    suspend fun getReviewsList(movieId: Int): Resource<List<Review>>
    suspend fun getSavedMoviesList(): Resource<List<MovieEntity>>
    suspend fun getSavedMovieDetail(movieId: Int): Resource<MovieEntity>
    suspend fun getSavedReviewsList(movieId: Int): Resource<List<ReviewEntity>>
    suspend fun insertMovie(movie: MovieEntity)
    suspend fun insertMovieDetail(movie: MovieEntity)
    suspend fun insertReviews(reviewsList: List<ReviewEntity>)
    suspend fun deleteMovie()
}
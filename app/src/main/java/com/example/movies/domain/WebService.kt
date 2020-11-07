package com.example.movies.domain

import com.example.movies.data.model.Movie
import com.example.movies.data.model.MoviesList
import com.example.movies.data.model.ReviewsList
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {

    @GET("discover/movie?")
    suspend fun getMovies(): MoviesList

    @GET("movie/{id}?")
    suspend fun getMovieDetail(@Path("id") id: Int?): Movie

    @GET("movie/{id}/reviews?")
    suspend fun getReviews(@Path("id") id: Int?): ReviewsList
}
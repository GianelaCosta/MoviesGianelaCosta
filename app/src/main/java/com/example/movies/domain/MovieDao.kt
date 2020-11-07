package com.example.movies.domain

import androidx.room.*
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.model.ReviewEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM moviesEntity")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM reviewsEntity WHERE movie_id = :movieId")
    suspend fun getMovieReviews(movieId: Int): List<ReviewEntity>

    @Query("SELECT * FROM moviesEntity WHERE id = :movieId")
    suspend fun getMovieDetail(movieId: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: ReviewEntity)

    @Delete()
    suspend fun deleteMovie(movie: MovieEntity)

    @Delete()
    suspend fun deleteReview(review: ReviewEntity)
}
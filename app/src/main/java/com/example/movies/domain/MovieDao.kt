package com.example.movies.domain

import androidx.room.*
import com.example.movies.data.model.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM moviesEntity")
    suspend fun getAllMovies():List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Delete()
    suspend fun deleteMovie(movie: MovieEntity)
}
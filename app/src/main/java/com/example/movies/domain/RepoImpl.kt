package com.example.movies.domain

import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.model.Review
import com.example.movies.data.model.ReviewEntity
import com.example.movies.vo.Resource
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoImpl @Inject constructor(private val dataSource: DataSource) : Repo {

    override suspend fun getMoviesList(): Resource<List<Movie>> {
        return dataSource.getMovies()
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<Movie> {
        return dataSource.getMovieDetail(movieId)
    }

    override suspend fun getReviewsList(movieId: Int): Resource<List<Review>> {
        return dataSource.getReviews(movieId)
    }

    override suspend fun getSavedMoviesList(): Resource<List<MovieEntity>> {
        return dataSource.getSavedMovies()
    }

    override suspend fun getSavedMovieDetail(movieId: Int): Resource<MovieEntity> {
        return dataSource.getSavedMovieDetail(movieId)
    }

    override suspend fun getSavedReviewsList(movieId: Int): Resource<List<ReviewEntity>> {
        return dataSource.getSavedReviews(movieId)
    }

    override suspend fun insertMovie(movie: MovieEntity) {
        dataSource.insertMovieRoom(movie)
    }

    override suspend fun insertMovieDetail(movie: MovieEntity) {
        dataSource.insertMovieDetailRoom(movie)
    }

    override suspend fun insertReviews(reviewsList: List<ReviewEntity>) {
        dataSource.insertReviewsRoom(reviewsList)
    }

    override suspend fun deleteMovie() {
        val expireTime = TimeUnit.HOURS.toMillis(24)
        val moviesList = dataSource.getSavedMovies().data
        for (movie in moviesList) {
            if (movie.createdTime < System.currentTimeMillis() - expireTime) {
                dataSource.deleteMovieRoom(movie)
            }
        }
    }
}
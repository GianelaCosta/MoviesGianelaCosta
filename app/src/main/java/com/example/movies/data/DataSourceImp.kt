package com.example.movies.data

import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.model.Review
import com.example.movies.data.model.ReviewEntity
import com.example.movies.domain.DataSource
import com.example.movies.domain.MovieDao
import com.example.movies.vo.Resource
import com.example.movies.vo.RetrofitClient
import javax.inject.Inject

class DataSourceImp @Inject constructor(private val movieDao: MovieDao) : DataSource {

    override suspend fun getMovies(): Resource<List<Movie>> {
        return Resource.Success(RetrofitClient.webservice.getMovies().moviesList)
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<Movie> {
        return Resource.Success(RetrofitClient.webservice.getMovieDetail(movieId))
    }

    override suspend fun getReviews(movieId: Int): Resource<List<Review>> {
        return Resource.Success(RetrofitClient.webservice.getReviews(movieId).reviewsList)
    }

    override suspend fun getSavedMovies(): Resource.Success<List<MovieEntity>> {
        return Resource.Success(movieDao.getAllMovies())
    }

    override suspend fun getSavedMovieDetail(movieId: Int): Resource<MovieEntity> {
        return Resource.Success(movieDao.getMovieDetail(movieId))
    }

    override suspend fun getSavedReviews(movieId: Int): Resource.Success<List<ReviewEntity>> {
        return Resource.Success(movieDao.getMovieReviews(movieId))
    }

    override suspend fun insertMovieRoom(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }

    override suspend fun insertMovieDetailRoom(movie: MovieEntity) {
        movieDao.insertMovieDetail(movie)
    }

    override suspend fun insertReviewsRoom(reviewsList: List<ReviewEntity>) {
        for (review in reviewsList) {
            movieDao.insertReview(review)
        }
    }

    override suspend fun deleteMovieRoom(movie: MovieEntity) {
        movieDao.deleteMovie(movie)
    }
}
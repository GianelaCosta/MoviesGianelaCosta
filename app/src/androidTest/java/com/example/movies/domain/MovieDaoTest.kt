package com.example.movies.domain

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.launchFragmentInHiltContainer
import com.example.movies.AppDatabase
import com.example.movies.data.model.Genre
import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.model.ReviewEntity
import com.example.movies.ui.DetailFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class MovieDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.movieDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun test() {
        val movie = Movie(
            724989,
            "https://image.tmdb.org/t/p/w500/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
            "https://image.tmdb.org/t/p/w500/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
            "Hard Kill",
            "he work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
            4.8F,
            "2115001",
            "2020-10-23",
            listOf<Genre>()
        )
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)
        launchFragmentInHiltContainer<DetailFragment>(bundle) {

        }
    }

    @Test
    fun insertMovie() = runBlockingTest {
        val movie = MovieEntity(
            724989,
            "https://image.tmdb.org/t/p/w500/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
            "https://image.tmdb.org/t/p/w500/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
            "Hard Kill",
            "he work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
            4.8F,
            "2115001",
            "2020-10-23",
            listOf<Genre>()
        )
        dao.insertMovie(movie)

        val allMovies = dao.getAllMovies()

        assertThat(allMovies.contains(movie))
    }

    @Test
    fun deleteMovie() = runBlockingTest {
        val movie = MovieEntity(
            724989,
            "https://image.tmdb.org/t/p/w500/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
            "https://image.tmdb.org/t/p/w500/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
            "Hard Kill",
            "he work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
            4.8F,
            "2115001",
            "2020-10-23",
            listOf<Genre>()
        )
        dao.insertMovie(movie)
        dao.deleteMovie(movie)

        val allMovies = dao.getAllMovies()

        assertThat(allMovies).doesNotContain(movie)
    }

    @Test
    fun insertMovieDetail() = runBlockingTest {
        val movie = MovieEntity(
            724989,
            "https://image.tmdb.org/t/p/w500/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
            "https://image.tmdb.org/t/p/w500/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
            "Hard Kill",
            "he work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
            4.8F,
            "2115001",
            "2020-10-23",
            listOf<Genre>(Genre("action"), Genre("comedy"))
        )
        dao.insertMovie(movie)

        val movieDetail = dao.getMovieDetail(724989)

        assertThat(movieDetail == movie)
    }

    @Test
    fun insertReview() = runBlockingTest {
        val review = ReviewEntity(
            "5fa3d112b2e074003e28f39a",
            724989,
            "Peter89Spencer",
            "This was truly fantastic! You'd expect Disney/Pixar to make this film, but Netflix beat em to it! And Ken Jeong voicing Gobi; hilarious! He was like the new Olaf. I truly enjoyed this film."
        )
        dao.insertReview(review)

        val reviews = dao.getMovieReviews(724989)

        assertThat(reviews.contains(review))
    }

    @Test
    fun deleteReview() = runBlockingTest {

        val review = ReviewEntity(
            "5fa3d112b2e074003e28f39a",
            724989,
            "Peter89Spencer",
            "This was truly fantastic! You'd expect Disney/Pixar to make this film, but Netflix beat em to it! And Ken Jeong voicing Gobi; hilarious! He was like the new Olaf. I truly enjoyed this film."
        )
        dao.insertReview(review)
        dao.deleteReview(review)

        val reviews = dao.getMovieReviews(724989)

        assertThat(reviews).doesNotContain(review)
    }
}
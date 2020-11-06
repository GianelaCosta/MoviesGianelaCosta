package com.example.movies.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.movies.AppDatabase
import com.example.movies.data.model.MovieEntity
import com.example.movies.data.model.ReviewEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.movieDao()
    }

    @After
    fun teardown() {
        database.close()
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
            "2020-10-23"
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
            "2020-10-23"
        )
        dao.insertMovie(movie)
        dao.deleteMovie(movie)

        val allMovies = dao.getAllMovies()

        assertThat(allMovies).doesNotContain(movie)
    }

    @Test
    fun getAllMovies() = runBlockingTest {
        val movie = MovieEntity(
            724989,
            "https://image.tmdb.org/t/p/w500/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
            "https://image.tmdb.org/t/p/w500/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
            "Hard Kill",
            "he work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
            4.8F,
            "2115001",
            "2020-10-23"
        )
        dao.insertMovie(movie)

        val allMovies = dao.getAllMovies()

        assertThat(allMovies).isEqualTo(listOf(movie))
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

    @Test
    fun getMovieReviews() = runBlockingTest {
        val review = ReviewEntity(
            "5fa3d112b2e074003e28f39a",
            724989,
            "Peter89Spencer",
            "This was truly fantastic! You'd expect Disney/Pixar to make this film, but Netflix beat em to it! And Ken Jeong voicing Gobi; hilarious! He was like the new Olaf. I truly enjoyed this film."
        )
        dao.insertReview(review)

        val reviews = dao.getMovieReviews(724989)

        assertThat(reviews).isEqualTo(listOf(review))
    }
}
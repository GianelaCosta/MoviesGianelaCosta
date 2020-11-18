package com.example.movies.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.MainCoroutineRule
import com.example.domain.FakeRepoImpl
import com.example.movies.data.model.Genre
import com.example.movies.data.model.MovieEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel
    private lateinit var fakeRepoImpl: FakeRepoImpl

    @Before
    fun setup() {
        fakeRepoImpl = FakeRepoImpl()
        viewModel = MainViewModel(fakeRepoImpl)
    }

    @Test
    fun fetchMovieList() {
        assertThat(Result.success(viewModel.fetchMoviesList))
    }

    @Test
    fun fetchMovieListFail() {
        fakeRepoImpl.setShouldReturnNetworkError(true)
        assertThat(viewModel.fetchMoviesList == Exception("Error"))
    }

    @Test
    fun saveMovie() {
        assertThat(
            Result.success(
                viewModel.saveMovie(
                    MovieEntity(
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
                )
            )
        )
    }

    @Test
    fun fetchDownloadedMoviesList() {
        assertThat(Result.success(viewModel.fetchDownloadedMoviesList()))
    }

    @Test
    fun fetchDownloadedMoviesListFail() {
        fakeRepoImpl.setShouldReturnNetworkError(true)
        assertThat(viewModel.fetchDownloadedMoviesList() == Exception("Error"))
    }

    @Test
    fun deleteExpiredDownloadedMovies() {
        assertThat(Result.success(viewModel.deleteExpiredDownloadedMovies()))
    }
}

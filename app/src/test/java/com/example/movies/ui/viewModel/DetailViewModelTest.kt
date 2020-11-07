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
class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        viewModel = DetailViewModel(FakeRepoImpl())
    }

    @Test
    fun fetchMovieDetail() {
        assertThat(Result.success(viewModel.fetchMovieDetail(635302)))
    }

    @Test
    fun saveMovieDetail() {
        assertThat(
            Result.success(
                viewModel.saveMovieDetail(
                    MovieEntity(
                        724989,
                        "https://image.tmdb.org/t/p/w500/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
                        "https://image.tmdb.org/t/p/w500/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
                        "Hard Kill",
                        "he work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
                        4.8F,
                        "2115001",
                        "2020-10-23",
                        listOf<Genre>(Genre("action"), Genre("drama"))
                    )
                )
            )
        )
    }

    @Test
    fun fetchDownloadedMovieDetail() {
        assertThat(Result.success(viewModel.fetchDownloadedMovieDetail(635302)))
    }
}
package com.example.movies.ui.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.MainCoroutineRule
import com.example.domain.FakeRepoImpl
import com.example.movies.data.model.ReviewEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ReviewViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ReviewViewModel
    private lateinit var fakeRepoImpl: FakeRepoImpl
    private val movieId = 635302

    @Before
    fun setup() {
        fakeRepoImpl = FakeRepoImpl()
        viewModel = ReviewViewModel(fakeRepoImpl)
    }

    @Test
    fun fetchReviewsList() {
        assertThat(Result.success(viewModel.fetchReviewsList(movieId)))
    }

    @Test
    fun fetchReviewsListFailNoNetwork() {
        fakeRepoImpl.setShouldReturnNetworkError(true)
        assertThat(viewModel.fetchReviewsList(movieId) == Exception("Error"))
    }

    @Test
    fun fetchReviewsListFailNoMovieFound() {
        assertThat(viewModel.fetchReviewsList(11) == Exception("Error"))
    }

    @Test
    fun saveMovieReviews() {
        assertThat(
            Result.success(
                viewModel.saveMovieReviews(
                    listOf(
                        ReviewEntity(
                            "5fa3d112b2e074003e28f36a",
                            635302,
                            "Peter89Spencer",
                            "This was truly fantastic! You'd expect Disney/Pixar to make this film, but Netflix beat em to it! And Ken Jeong voicing Gobi; hilarious! He was like the new Olaf. I truly enjoyed this film."
                        ),
                        ReviewEntity(
                            "5fa3d112b2e074003e28f39a",
                            635302,
                            "Alice",
                            "Lovely story, very enjoyable"
                        )
                    )
                )
            )
        )
    }

    @Test
    fun fetchDownloadedReviewsList() {
        assertThat(Result.success(viewModel.fetchDownloadedReviewsList(movieId)))
    }

    @Test
    fun fetchDownloadedReviewsListFailNoNetwork() {
        fakeRepoImpl.setShouldReturnNetworkError(true)
        assertThat(viewModel.fetchDownloadedReviewsList(movieId) == Exception("Error"))
    }

    @Test
    fun fetchDownloadedReviewsListFailNoMovieFound() {
        assertThat(viewModel.fetchDownloadedReviewsList(11) == Exception("Error"))
    }
}
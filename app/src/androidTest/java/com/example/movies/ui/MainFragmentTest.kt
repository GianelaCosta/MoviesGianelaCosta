package com.example.movies.ui


import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.domain.FakeRepoImplAndroidTest
import com.example.launchFragmentInHiltContainer
import com.example.movies.R
import com.example.movies.data.model.Movie
import com.example.movies.ui.viewModel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MainFragmentTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun click_on_movie() {
        val navController = mock(NavController::class.java)
        val movie = Movie()
        val testViewModel = MainViewModel(FakeRepoImplAndroidTest())
        launchFragmentInHiltContainer<MainFragment> {
            Navigation.setViewNavController(requireView(), navController)
            rv_movies.adapter = MainAdapter(requireContext(), listOf(movie), this)
        }

        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MainAdapter.MainViewHolder>(
                0,
                click()
            )
        )
        assert(true)
    }


}
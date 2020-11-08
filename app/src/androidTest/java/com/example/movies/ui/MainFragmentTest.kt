package com.example.movies.ui


import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.example.domain.FakeMovieData
import com.example.launchFragmentInHiltContainer
import com.example.movies.R
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

//    @get: Rule
//    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    val MOVIE_IN_TEST = FakeMovieData.movieList[0]
//
//    @Test
//    fun listFragmentVisibleOnAppLaunch() {
//
//        launchFragmentInHiltContainer<MainFragment> {
//            rv_movies.adapter = MainAdapter(requireContext(), listOf(), this)     }
//        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
//    }

    @Test
    fun displayMovies() {

        launchFragmentInHiltContainer<MainFragment> {
            rv_movies.adapter = MainAdapter(requireContext(), FakeMovieData.movieList, this)
        }

//        onView(withId(R.id.rv_movies))
//            .check(withRowContaining(withText("Expected text in a row")))

//        onView(withId(R.id.rv_movies)).
//            .check(matches(hasDescendant(withText("Demon Slayer: Kimetsu no Yaiba - The Movie: Mugen Train"))))

//        onView(withId(R.id.rv_movies))
//            .check(matches(TestUtils().atPosition(0, withText("Demon Slayer: Kimetsu no Yaiba - The Movie: Mugen Train"))));
    }

    @Test
    fun click_on_movie() {
//
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<MainFragment> {
            Navigation.setViewNavController(requireView(), navController)
            rv_movies.adapter = MainAdapter(requireContext(), FakeMovieData.movieList, this)
        }
        onView(withId(R.id.rv_movies))
            .perform(actionOnItemAtPosition<MainAdapter.MainViewHolder>(0, click()))

        onView(withId(R.id.movie_title)).check(matches(withText(MOVIE_IN_TEST.name)))
    }
}

package com.example.movies.ui

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.example.domain.FakeMovieData
import com.example.launchFragmentInHiltContainer
import com.example.movies.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class DetailFragmentTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    private val movie = FakeMovieData.movieList[0]

    @Test
    fun displayMovieTitle() {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)

        launchFragmentInHiltContainer<DetailFragment>(bundle) {
            movie_title.text = movie.name
        }

        onView(withId(R.id.movie_title))
            .check(matches(withText("Demon Slayer: Kimetsu no Yaiba - The Movie: Mugen Train")))
    }

    @Test
    fun displayMoviePopularity() {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)

        launchFragmentInHiltContainer<DetailFragment>(bundle) {
            movie_popularity.text = getString(R.string.popularity_text, movie.popularity)
        }

        onView(withId(R.id.movie_popularity))
            .check(matches(withText("1986559 people watching")))
    }

    @Test
    fun displayMovieRate() {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)

        launchFragmentInHiltContainer<DetailFragment>(bundle) {
            movie_ratingNo.text = movie.rate.toString()
        }

        onView(withId(R.id.movie_ratingNo))
            .check(matches(withText("6.7")))
    }

    @Test
    fun displayMovieGenres() {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)

        launchFragmentInHiltContainer<DetailFragment>(bundle) {
            var genresText = ""
            for (genre in movie.genres!!) {
                genresText += " " + genre.genreName
            }
            movie_genres.text = genresText
        }

        onView(withId(R.id.movie_genres))
            .check(matches(withText(" action drama")))
    }

    @Test
    fun displayMovieDescription() {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)

        launchFragmentInHiltContainer<DetailFragment>(bundle) {
            movie_detail.text = movie.description
        }

        onView(withId(R.id.movie_detail))
            .check(matches(withText("Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!")))
    }

    /**
     * Unfortunately could't make navigation tests to work, it is a matter of how hilt injects the
     * dependencies, I am not able to set a test viewModel so data is not fetch and view does not load,
     * also navConroller is null even I am using a mock
     */

//    @Test
//    fun clickOnMovie_navigateToDetailFragment() {
//        val navController = mock(NavController::class.java)
//        val bundle = Bundle()
//        bundle.putParcelable("movie", movie)
//
//        launchFragmentInHiltContainer<DetailFragment>(bundle) {
//            navController.setGraph(R.navigation.main_graph)
//            Navigation.setViewNavController(requireView(), navController)
//        }
//
//        onView(withId(R.id.btn_show_reviews)).perform(click())
//
//        verify(navController).navigate(
//            R.id.action_detailFragment_to_revirewFragment)
//    }
//
//    @Test
//    fun pressBackButton_popBackStack() {
//    val bundle = Bundle()
//    bundle.putParcelable("movie", movie)
//    val navController = Mockito.mock(NavController::class.java)
//
//    launchFragmentInHiltContainer<DetailFragment>(bundle) {
//        navController.setGraph(R.navigation.main_graph)
//        Navigation.setViewNavController(requireView(), navController)}
//
//    navController.navigate(R.id.action_detailFragment_to_revirewFragment, bundle)
//    onView(withId(R.id.revirewFragment)).perform(ViewActions.pressBack())
//
//    assertThat(navController.currentDestination?.displayName).isEqualTo(R.id.detailFragment)
//    }
}


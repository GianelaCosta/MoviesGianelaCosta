package com.example.movies.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.launchFragmentInHiltContainer
import com.example.movies.R
import com.example.movies.data.model.Genre
import com.example.movies.data.model.Movie
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class DetailFragmentTest {

    ///////////////////////////////////////////////////////////////////////////////

//    @Test
//    fun isMovieDataVisible(){
//
//        val movieId = 724989
//        val movieTitle = "Hard Kill"
//        val movieDescription = "he work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
//        val movieRate = 4.8F
//        val moviePopularity = "2115001"
//        val movieReleaseDate = "2020-10-23"
//        val movieGenres = listOf<String>("action", "drama")
//
//        val movie = Movie(
//            724989,
//            "https://image.tmdb.org/t/p/w500/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
//            "https://image.tmdb.org/t/p/w500/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
//            "Hard Kill",
//            "he work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
//            4.8F,
//            "2115001",
//            "2020-10-23",
//            listOf<Genre>(Genre("action"), Genre("drama"))
//        )
//
//        val movieDataSource: mockk<DataSource>()
//    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickOnMovie_navigateToDetailFragment() {
        val navController = mock(NavController::class.java)

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
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.btn_show_reviews)).perform(click())

        verify(navController).navigate(
            R.id.action_detailFragment_to_revirewFragment
        )
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)

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
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }
}


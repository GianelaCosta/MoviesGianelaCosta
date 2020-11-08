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
            movie_popularity.text = movie.popularity + " people watching"
        }

        onView(withId(R.id.movie_popularity))
            .check(matches(withText("1986559 people watching")))
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
    fun displayMovieDescription() {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)

        launchFragmentInHiltContainer<DetailFragment>(bundle) {
            movie_detail.text = movie.description
        }

        onView(withId(R.id.movie_detail))
            .check(matches(withText("Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!")))
    }

//    @Test
//    fun clickOnMovie_navigateToDetailFragment() {
//        val navController = mock(NavController::class.java)
//        val bundle = Bundle()
//        bundle.putParcelable("movie", movie)
//
//        launchFragmentInHiltContainer<DetailFragment>(bundle) {
//            Navigation.setViewNavController(requireView(), navController)
//        }
//
//        onView(withId(R.id.btn_show_reviews)).perform(click())
//
//        verify(navController).navigate(
//            R.id.action_detailFragment_to_revirewFragment
//        )
//    }
//
//
//    @Test
//    fun pressBackButton_popBackStack() {
//        val navController = mock(NavController::class.java)
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
//            listOf<Genre>()
//        )
//        val bundle = Bundle()
//        bundle.putParcelable("movie", movie)
//
//        launchFragmentInHiltContainer<DetailFragment>(bundle) {
//            Navigation.setViewNavController(requireView(), navController)
//        }
//
//        pressBack()
//        verify(navController).popBackStack()
//    }
}


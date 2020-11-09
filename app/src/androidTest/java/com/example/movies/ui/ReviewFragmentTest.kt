package com.example.movies.ui

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.domain.FakeMovieData
import com.example.launchFragmentInHiltContainer
import com.example.movies.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_review.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ReviewFragmentTest {

    @get: Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    private val movie = FakeMovieData.movieList[0]

    private val reviewList = FakeMovieData.reviewList

    @Test
    fun displayNumberOfReviews() {
        val bundle = Bundle()
        bundle.putParcelable("movie", movie)

        launchFragmentInHiltContainer<ReviewFragment>(bundle) {
            txt_reviews_found.text = "reviews(" + reviewList.size + ")"
        }

        onView(withId(R.id.txt_reviews_found))
            .check(ViewAssertions.matches(ViewMatchers.withText("reviews(2)")))
    }

    /**
     * Unfortunately could't test further this fragment is correctly displayed
     * or make navigation tests to work, from one side found that there are many
     * work around to test recyclerviews with espresso but no one worked, in addition
     * due to how hilt injects the dependencies navigation testing was not possible
     */

}


package com.example.movies.ui


import androidx.test.filters.MediumTest
import com.example.domain.FakeMovieData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule


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

    val movie = FakeMovieData.movieList[0]

    /**
     * Unfortunately could't test this fragment is correctly displayed
     * or make navigation tests to work, from one side found that there are many
     * work around to test recyclerviews with espresso but no one worked, in addition
     * due to how hilt injects the dependencies navigation testing was not possible
     */

//    @Test
//    fun listFragmentVisibleOnAppLaunch() {
//
//        launchFragmentInHiltContainer<MainFragment> {
//            rv_movies.adapter = MainAdapter(requireContext(), listOf(), this)
//        }
//
//        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
//    }
//
//    @Test
//    fun displayMovies() {
//
//        val bundle = Bundle()
//        bundle.putParcelable("movie", movie)
//        val navController = mock(NavController::class.java)
//        launchFragmentInHiltContainer<MainFragment> {
//            navController.setGraph(R.navigation.main_graph)
//            Navigation.setViewNavController(requireView(), navController)
//            rv_movies.adapter = MainAdapter(requireContext(), FakeMovieData.movieList, this)
//        }
//
//        onData(withId(R.id.rv_movies)).atPosition(0).onChildView(withChild(withId(R.id.txt_movie_name))).check(matches(
//            withText(movie.name)))
//
//    }
//
//    @Test
//    fun pressBackButton_popBackStack() {
//        val bundle = Bundle()
//        bundle.putParcelable("movie", Movie())
//        val navController = mock(NavController::class.java)
//        launchFragmentInHiltContainer<MainFragment> {
//            navController.setGraph(R.navigation.main_graph)
//            Navigation.setViewNavController(requireView(), navController)
//            rv_movies.adapter = MainAdapter(requireContext(), FakeMovieData.movieList, this)
//        }
//        navController.navigate(R.id.action_mainFragment_to_detailFragment, bundle)
//        pressBack()
//
//        verify(navController).popBackStack()
//    }
}

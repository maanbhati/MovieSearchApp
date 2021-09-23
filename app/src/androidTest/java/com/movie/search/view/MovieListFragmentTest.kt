package com.movie.search.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.movie.search.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class MovieListFragmentTest {

    @Test
    fun test_movie_list_fragment_with_id_perform_click() {
        launchFragmentInHiltContainer<MovieListFragment>()
        onView(withId(R.id.movieListFragment)).perform(click())
    }
}
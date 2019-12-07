package com.marcio.popularmoviesclean

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.marcio.popularmoviesclean.android.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesEndToEndTest {

    @get:Rule
    val rule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, false)

    @Test
    fun givenLoadedMoviesWhenMovieIsClickedThenShouldDisplayMovieDetails() {

        rule.launchActivity(Intent())

        Thread.sleep(2000)

        onView(withContentDescription("Joker")).perform(click())

        onView(withId(R.id.titleView)).check(matches(withText("Joker")))
    }
}

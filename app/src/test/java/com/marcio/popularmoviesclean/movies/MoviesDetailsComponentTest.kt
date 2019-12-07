package com.marcio.popularmoviesclean.movies

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.marcio.popularmoviesclean.MoviesApplication
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.TestActivity
import com.marcio.popularmoviesclean.TestApplication
import com.marcio.popularmoviesclean.TestData
import com.marcio.popularmoviesclean.movies.gateway.HttpMoviesGateway
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayJsonParser
import com.marcio.popularmoviesclean.movies.presentation.FakeImageLoader
import com.marcio.popularmoviesclean.movies.presentation.details.MovieDetailsFragment
import com.marcio.popularmoviesclean.state.FakeDispatcherFactory
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class MoviesDetailsComponentTest {

    @get:Rule
    val rule: ActivityTestRule<TestActivity> = ActivityTestRule(TestActivity::class.java)

    @Test
    fun `Given selected movies When details screen appears Then show display movie details`() {
        val moviesJson = TestData.JSON_POPULAR_MOVIES_RESPONSE.trimIndent()
        val selectedMovie = TestData.POPULAR_MOVIES[0]
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(200).setBody(moviesJson))

        val baseUrl = server.url("/").toString()
        rule.activity.testDependencyManager = MoviesApplication.Builder()
            .registerMoviesGateway(lazy {
                HttpMoviesGateway(
                    baseUrl,
                    "/",
                    500,
                    "123",
                    httpClient = OkHttpClient(),
                    gatewayParser = MoviesGatewayJsonParser()
                )
            })
            .registerDispatcherFactory(lazy { FakeDispatcherFactory() })
            .registerImageLoader(lazy { FakeImageLoader() })
            .start()

        (rule.activity.testDependencyManager as MoviesApplication).moviesMainUseCases.selectMovie(
            selectedMovie.id
        )

        rule.activity.showFragment(MovieDetailsFragment())

        onView(withId(R.id.titleView)).check(matches(withText(selectedMovie.title)))
        onView(withId(R.id.releaseDateView)).check(matches(withText(selectedMovie.releaseDate)))
        onView(withId(R.id.averageRatingView)).check(matches(withText(selectedMovie.averageRating)))
        onView(withId(R.id.userRatingView)).check(matches(withText(selectedMovie.ratingsCount)))
        onView(withId(R.id.synopsisView)).check(matches(withText(selectedMovie.synopsis)))
        onView(withId(R.id.posterView)).check(matches(withContentDescription(selectedMovie.title)))

        server.shutdown()
    }
}

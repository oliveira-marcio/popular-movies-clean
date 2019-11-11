package com.marcio.popularmoviesclean.movies.presentation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.marcio.popularmoviesclean.MoviesApplication
import com.marcio.popularmoviesclean.TestActivity
import com.marcio.popularmoviesclean.TestApplication
import com.marcio.popularmoviesclean.TestData
import com.marcio.popularmoviesclean.movies.gateway.HttpMoviesGateway
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayJsonParser
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
class MoviesMainComponentTest {

    @get:Rule
    val rule: ActivityTestRule<TestActivity> = ActivityTestRule(TestActivity::class.java)

    @Test
    fun `Given available movies When main screen appears Then show movies list`() {
        val moviesJson = TestData.JSON_POPULAR_MOVIES_RESPONSE.trimIndent()
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(200).setBody(moviesJson))

        val baseUrl = server.url("/").toString()

        rule.activity.testDependencyManager = MoviesApplication.Builder()
            .registerMoviesGateway(lazy {
                HttpMoviesGateway(
                    baseUrl,
                    "123",
                    httpClient = OkHttpClient(),
                    gatewayParser = MoviesGatewayJsonParser()
                )
            })
            .registerDispatcherFactory(lazy { FakeDispatcherFactory() })
            .start()

        rule.activity.showFragment(MoviesMainFragment())

        onView(withText("Suicide Squad")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withText("Jason Bourne")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        server.shutdown()
    }
}

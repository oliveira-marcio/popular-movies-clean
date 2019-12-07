package com.marcio.popularmoviesclean.movies

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.marcio.popularmoviesclean.MoviesApplication
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.TestActivity
import com.marcio.popularmoviesclean.TestApplication
import com.marcio.popularmoviesclean.TestData
import com.marcio.popularmoviesclean.movies.gateway.HttpMoviesGateway
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayJsonParser
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.movies.presentation.FakeImageLoader
import com.marcio.popularmoviesclean.movies.presentation.main.MoviesMainFragment
import com.marcio.popularmoviesclean.state.FakeDispatcherFactory
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.allOf
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class MoviesMainComponentTest {

    @get:Rule
    val rule: ActivityTestRule<TestActivity> = ActivityTestRule(TestActivity::class.java)

    @Test
    fun `Given available movies When main screen appears Then show popular movies list`() {
        val moviesJson = TestData.JSON_POPULAR_MOVIES_RESPONSE.trimIndent()
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

        rule.activity.showFragment(MoviesMainFragment())

        onView(withContentDescription("Suicide Squad")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withContentDescription("Jason Bourne")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        server.shutdown()
    }

    @Test
    fun `Given a non successful response When main screen appears Then show error message`() {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(500))

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

        rule.activity.showFragment(MoviesMainFragment())

        onView(withId(R.id.errorView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        server.shutdown()
    }

    @Test
    fun `Given error loading movies When refresh is clicked Then show movies list`() {
        val moviesJson = TestData.JSON_POPULAR_MOVIES_RESPONSE.trimIndent()
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(500))
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

        rule.activity.showFragment(MoviesMainFragment())

        onView(withId(R.id.errorView)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.actionRefresh)).perform(click())

        onView(withId(R.id.errorView)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withContentDescription("Suicide Squad")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withContentDescription("Jason Bourne")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        server.shutdown()
    }

    @Test
    fun `Given loaded popular movies And error loading top rated movies When top rated is clicked Then show warning message`() {
        val moviesJson = TestData.JSON_POPULAR_MOVIES_RESPONSE.trimIndent()
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(200).setBody(moviesJson))
        server.enqueue(MockResponse().setResponseCode(500))

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

        rule.activity.showFragment(MoviesMainFragment())

        onView(withContentDescription("Suicide Squad")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withContentDescription("Jason Bourne")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        rule.activity.dependencyManager.moviesMainUseCases.loadMovies(Movies.Category.TOP_RATED)

        val context = ApplicationProvider.getApplicationContext<TestApplication>()

        assertEquals(context.getString(R.string.error_unknown), ShadowToast.getTextOfLatestToast())

        server.shutdown()
    }

    @Test
    fun `Given loaded popular movies When top rated movies is clicked Then show top rated movies list`() {
        val popularMoviesJson = TestData.JSON_POPULAR_MOVIES_RESPONSE.trimIndent()
        val topRatedMoviesJson = TestData.JSON_TOP_RATED_MOVIES_RESPONSE.trimIndent()
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(200).setBody(popularMoviesJson))
        server.enqueue(MockResponse().setResponseCode(200).setBody(topRatedMoviesJson))

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

        rule.activity.showFragment(MoviesMainFragment())

        onView(withContentDescription("Suicide Squad")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withContentDescription("Jason Bourne")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        rule.activity.dependencyManager.moviesMainUseCases.loadMovies(Movies.Category.TOP_RATED)

        onView(withContentDescription("The Shawshank Redemption")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withContentDescription("Whiplash")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        server.shutdown()
    }

    @Test
    fun `Given loaded top rated movies When popular movies is clicked Then show popular movies list`() {
        val popularMoviesJson = TestData.JSON_POPULAR_MOVIES_RESPONSE.trimIndent()
        val topRatedMoviesJson = TestData.JSON_TOP_RATED_MOVIES_RESPONSE.trimIndent()
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(200).setBody(topRatedMoviesJson))
        server.enqueue(MockResponse().setResponseCode(200).setBody(popularMoviesJson))

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

        rule.activity.showFragment(MoviesMainFragment())

        onView(withContentDescription("The Shawshank Redemption")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withContentDescription("Whiplash")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        rule.activity.dependencyManager.moviesMainUseCases.loadMovies(Movies.Category.POPULAR)

        onView(withContentDescription("Suicide Squad")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withContentDescription("Jason Bourne")).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        server.shutdown()
    }

    @Test
    fun `Given loaded movies When list is scrolled to the end Then should load more movies`() {
        val firstMoviesJson = TestData.JSON_POPULAR_MOVIES_RESPONSE.trimIndent()
        val secondMoviesJson = TestData.JSON_TOP_RATED_MOVIES_RESPONSE.trimIndent()
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(200).setBody(firstMoviesJson))
        server.enqueue(MockResponse().setResponseCode(200).setBody(secondMoviesJson))

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

        rule.activity.showFragment(MoviesMainFragment())

        onView(withId(R.id.moviesGrid)).check(
            matches(
                allOf(
                    hasChildCount(2),
                    hasDescendant(withContentDescription("Suicide Squad")),
                    hasDescendant(withContentDescription("Jason Bourne"))
                )
            )
        )

        (rule.activity.testDependencyManager as MoviesApplication).moviesMainPresenter.onListScroll(
            true,
            2,
            1
        )

        onView(withId(R.id.moviesGrid)).check(
            matches(
                allOf(
                    hasChildCount(4),
                    hasDescendant(withContentDescription("Suicide Squad")),
                    hasDescendant(withContentDescription("Jason Bourne")),
                    hasDescendant(withContentDescription("The Shawshank Redemption")),
                    hasDescendant(withContentDescription("Whiplash"))
                )
            )
        )

        server.shutdown()
    }

    @Test
    fun `Given loaded movies and error loading more movies When list is scrolled again to the end Then should load more movies`() {
        val firstMoviesJson = TestData.JSON_POPULAR_MOVIES_RESPONSE.trimIndent()
        val secondMoviesJson = TestData.JSON_TOP_RATED_MOVIES_RESPONSE.trimIndent()
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(200).setBody(firstMoviesJson))
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200).setBody(secondMoviesJson))

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

        rule.activity.showFragment(MoviesMainFragment())

        onView(withId(R.id.moviesGrid)).check(
            matches(
                allOf(
                    hasChildCount(2),
                    hasDescendant(withContentDescription("Suicide Squad")),
                    hasDescendant(withContentDescription("Jason Bourne"))
                )
            )
        )

        (rule.activity.testDependencyManager as MoviesApplication).moviesMainPresenter.onListScroll(
            true,
            2,
            1
        )

        val context = ApplicationProvider.getApplicationContext<TestApplication>()
        assertEquals(context.getString(R.string.error_unknown), ShadowToast.getTextOfLatestToast())

        (rule.activity.testDependencyManager as MoviesApplication).moviesMainPresenter.onListScroll(
            true,
            2,
            1
        )

        onView(withId(R.id.moviesGrid)).check(
            matches(
                allOf(
                    hasChildCount(4),
                    hasDescendant(withContentDescription("Suicide Squad")),
                    hasDescendant(withContentDescription("Jason Bourne")),
                    hasDescendant(withContentDescription("The Shawshank Redemption")),
                    hasDescendant(withContentDescription("Whiplash"))
                )
            )
        )

        server.shutdown()
    }
}

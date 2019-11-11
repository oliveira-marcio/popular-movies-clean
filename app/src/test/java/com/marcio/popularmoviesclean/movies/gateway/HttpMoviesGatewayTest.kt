package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.TestData
import com.marcio.popularmoviesclean.gateway.FakeMoviesGatewayJsonParser
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class HttpMoviesGatewayTest {
    @Test
    fun `Given there is internet connection When get popular movies is called Then TMDB API with popular path is called with GET method`() {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        val moviesList = TestData.POPULAR_MOVIES
        val baseUrl = server.url("/").toString()

        val service = HttpMoviesGateway(
            baseUrl,
            "123",
            "pt-BR",
            OkHttpClient(),
            FakeMoviesGatewayJsonParser(moviesList)
        )

        assertEquals(moviesList, service.getPopularMovies())

        val request = server.takeRequest()

        assertEquals("GET", request.method)
        assertEquals("/popular/movies?api_key=123&language=pt-BR", request.path)

        server.shutdown()
    }

    @Test
    fun `Given there is internet connection When get top rated movies is called Then TMDB API with top rated path is called with GET method`() {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        val moviesList = TestData.POPULAR_MOVIES
        val baseUrl = server.url("/").toString()

        val service = HttpMoviesGateway(
            baseUrl,
            "123",
            "pt-BR",
            OkHttpClient(),
            FakeMoviesGatewayJsonParser(moviesList)
        )

        assertEquals(moviesList, service.getTopRatedMovies())

        val request = server.takeRequest()

        assertEquals("GET", request.method)
        assertEquals("/top_rated/movies?api_key=123&language=pt-BR", request.path)

        server.shutdown()
    }

    @Test
    fun `Given a valid request When get popular movies is called And a non successful response is received Then should raise an illegal state exception`() {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setResponseCode(500))

        val baseUrl = server.url("/").toString()

        val service = HttpMoviesGateway(
            baseUrl,
            "123",
            "pt-BR",
            OkHttpClient(),
            FakeMoviesGatewayJsonParser()
        )

        try {
            service.getPopularMovies()
            Assert.fail("Should throw an illegal state exception")
        } catch (_: IllegalStateException) {
        }

        server.shutdown()
    }
}

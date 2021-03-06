package com.marcio.popularmoviesclean.movies.gateway

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.marcio.popularmoviesclean.TestApplication
import com.marcio.popularmoviesclean.TestData
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = TestApplication::class)
class MoviesGatewayJsonParserTest {
    @Test
    fun `Given a movies json When parser is called Then should return a list of movies`() {
        val json = TestData.JSON_POPULAR_MOVIES_RESPONSE.trimIndent()
        val moviesList = TestData.POPULAR_MOVIES
        val posterBaseUrl = "http://image.tmdb.org/t/p/"
        val jsonParser = MoviesGatewayJsonParser()

        assertEquals(moviesList, jsonParser.parseMovies(json, posterBaseUrl, 500))
    }

    @Test
    fun `Given a movies json with no poster path When parser is called Then should return a list of movies with poster url empty`() {
        val json = TestData.JSON_POPULAR_MOVIES_NO_POSTER_RESPONSE.trimIndent()
        val moviesList = TestData.POPULAR_MOVIES_NO_POSTER
        val posterBaseUrl = "http://image.tmdb.org/t/p/"
        val jsonParser = MoviesGatewayJsonParser()

        assertEquals(moviesList, jsonParser.parseMovies(json, posterBaseUrl, 500))
    }
}

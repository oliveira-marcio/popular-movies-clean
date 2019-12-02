package com.marcio.popularmoviesclean.movies.models

import com.marcio.popularmoviesclean.TestData
import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesTest {
    @Test
    fun `Given list of movies and existent id When selected movie is requested Then return selected movie`() {
        val selectedMovie = TestData.POPULAR_MOVIES[0]
        val movies = Movies(
            TestData.POPULAR_MOVIES,
            selectedMovieId = selectedMovie.id
        )

        assertEquals(selectedMovie, movies.selectedMovie)
    }

    @Test
    fun `Given list of movies and existent id When is movie selected is requested Then return true`() {
        val selectedMovie = TestData.POPULAR_MOVIES[0]
        val movies = Movies(
            TestData.POPULAR_MOVIES,
            selectedMovieId = selectedMovie.id
        )

        assertEquals(true, movies.isSelected)
    }

    @Test
    fun `Given list of movies and nonexistent id When is movie selected is requested Then return false`() {
        val movies = Movies(
            TestData.POPULAR_MOVIES,
            selectedMovieId = "invalid"
        )

        assertEquals(null, movies.selectedMovie)
    }

    @Test
    fun `Given list of movies and nonexistent id When selected movie is requested Then return null`() {
        val movies = Movies(
            TestData.POPULAR_MOVIES,
            selectedMovieId = "invalid"
        )

        assertEquals(false, movies.isSelected)
    }
}

package com.marcio.popularmoviesclean.movies.presentation

import com.marcio.popularmoviesclean.movies.models.Movie
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieItemViewModelTest {
    @Test
    fun `Given a movie title When title is requested Then return title`() {
        val viewModel = MovieItemViewModel(
            Movie("Star Wars"),
            MovieItemPlaceHolder("N/A", -1)
        )
        assertEquals("Star Wars", viewModel.title)
    }

    @Test
    fun `Given an empty movie title When title is requested Then return replacement text`() {
        val viewModel = MovieItemViewModel(
            Movie(),
            MovieItemPlaceHolder("N/A", -1)
        )
        assertEquals("N/A", viewModel.title)
    }

    @Test
    fun `Given a space only title When title is requested Then return replacement text`() {
        val viewModel = MovieItemViewModel(
            Movie("    "),
            MovieItemPlaceHolder("N/A", -1)
        )
        assertEquals("N/A", viewModel.title)
    }
}

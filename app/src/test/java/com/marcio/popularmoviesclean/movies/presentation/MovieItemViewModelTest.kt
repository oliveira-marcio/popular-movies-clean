package com.marcio.popularmoviesclean.movies.presentation

import com.marcio.popularmoviesclean.movies.models.Movie
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieItemViewModelTest {
    @Test
    fun `Given a movie title When content description is requested Then return title`() {
        val viewModel = MovieItemViewModel(
            Movie(title = "Star Wars"),
            MovieItemPlaceHolder("N/A")
        )
        assertEquals("Star Wars", viewModel.contentDescription)
    }

    @Test
    fun `Given an empty movie title When content description is requested Then return replacement text`() {
        val viewModel = MovieItemViewModel(
            Movie(),
            MovieItemPlaceHolder("N/A")
        )
        assertEquals("N/A", viewModel.contentDescription)
    }

    @Test
    fun `Given a space only title When content description is requested Then return replacement text`() {
        val viewModel = MovieItemViewModel(
            Movie(title = "    "),
            MovieItemPlaceHolder("N/A")
        )
        assertEquals("N/A", viewModel.contentDescription)
    }
}

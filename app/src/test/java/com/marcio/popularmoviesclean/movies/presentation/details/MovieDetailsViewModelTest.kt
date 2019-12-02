package com.marcio.popularmoviesclean.movies.presentation.details

import com.marcio.popularmoviesclean.movies.models.Movie
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieDetailsViewModelTest {
    @Test
    fun `Given a movie title When title is requested Then return title`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(title = "Star Wars"),
                MovieDetailsPlaceHolder(title = "N/A")
            )

        assertEquals("Star Wars", viewModel.title)
    }

    @Test
    fun `Given an empty movie title When title is requested Then return replacement text`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(),
                MovieDetailsPlaceHolder(title = "N/A")
            )

        assertEquals("N/A", viewModel.title)
    }

    @Test
    fun `Given a space only title When title is requested Then return replacement text`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(title = "    "),
                MovieDetailsPlaceHolder(title = "N/A")
            )

        assertEquals("N/A", viewModel.title)
    }

    @Test
    fun `Given a movie synopsis When synopsis is requested Then return synopsis`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(synopsis = "Star Wars"),
                MovieDetailsPlaceHolder(synopsis = "N/A")
            )

        assertEquals("Star Wars", viewModel.synopsis)
    }

    @Test
    fun `Given an empty movie title When synopsis is requested Then return replacement text`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(),
                MovieDetailsPlaceHolder(synopsis = "N/A")
            )

        assertEquals("N/A", viewModel.synopsis)
    }

    @Test
    fun `Given a space only synopsis When synopsis is requested Then return replacement text`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(synopsis = "    "),
                MovieDetailsPlaceHolder(synopsis = "N/A")
            )

        assertEquals("N/A", viewModel.synopsis)
    }

    @Test
    fun `Given a movie average rating When average rating is requested Then return average rating`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(averageRating = "Star Wars"),
                MovieDetailsPlaceHolder(averageRating = "N/A")
            )

        assertEquals("Star Wars", viewModel.averageRating)
    }

    @Test
    fun `Given an empty movie average rating When average rating is requested Then return replacement text`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(),
                MovieDetailsPlaceHolder(averageRating = "N/A")
            )

        assertEquals("N/A", viewModel.averageRating)
    }

    @Test
    fun `Given a space only average rating When average rating is requested Then return replacement text`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(averageRating = "    "),
                MovieDetailsPlaceHolder(averageRating = "N/A")
            )

        assertEquals("N/A", viewModel.averageRating)
    }

    @Test
    fun `Given a movie ratings count When ratings count is requested Then return ratings count`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(ratingsCount = "Star Wars"),
                MovieDetailsPlaceHolder(ratingsCount = "N/A")
            )

        assertEquals("Star Wars", viewModel.ratingsCount)
    }

    @Test
    fun `Given an empty movie ratings count When ratings count is requested Then return replacement text`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(),
                MovieDetailsPlaceHolder(ratingsCount = "N/A")
            )

        assertEquals("N/A", viewModel.ratingsCount)
    }

    @Test
    fun `Given a space only ratings count When ratings count is requested Then return replacement text`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(ratingsCount = "    "),
                MovieDetailsPlaceHolder(ratingsCount = "N/A")
            )

        assertEquals("N/A", viewModel.ratingsCount)
    }

    @Test
    fun `Given a movie release date When release date is requested Then return release date`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(releaseDate = "Star Wars"),
                MovieDetailsPlaceHolder(releaseDate = "N/A")
            )

        assertEquals("Star Wars", viewModel.releaseDate)
    }

    @Test
    fun `Given an empty movie release date When release date is requested Then return replacement text`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(),
                MovieDetailsPlaceHolder(releaseDate = "N/A")
            )

        assertEquals("N/A", viewModel.releaseDate)
    }

    @Test
    fun `Given a space only release date When release date is requested Then return replacement text`() {
        val viewModel =
            MovieDetailsViewModel(
                Movie(releaseDate = "    "),
                MovieDetailsPlaceHolder(releaseDate = "N/A")
            )

        assertEquals("N/A", viewModel.releaseDate)
    }
}

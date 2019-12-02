package com.marcio.popularmoviesclean.movies.presentation.details

import com.marcio.popularmoviesclean.movies.models.Movie

data class MovieDetailsViewModel(
    private val selectedMovie: Movie,
    private val detailsPlaceHolder: MovieDetailsPlaceHolder
) {
    val title = getValueOrReplacement(selectedMovie.title, detailsPlaceHolder.title)
    val synopsis = getValueOrReplacement(selectedMovie.synopsis, detailsPlaceHolder.synopsis)
    val averageRating =
        getValueOrReplacement(selectedMovie.averageRating, detailsPlaceHolder.averageRating)
    val ratingsCount =
        getValueOrReplacement(selectedMovie.ratingsCount, detailsPlaceHolder.ratingsCount)
    val releaseDate =
        getValueOrReplacement(selectedMovie.releaseDate, detailsPlaceHolder.releaseDate)

    private fun getValueOrReplacement(value: String, replacement: String) =
        if (value.isNotBlank()) value else replacement
}

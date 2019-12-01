package com.marcio.popularmoviesclean.movies.presentation

import com.marcio.popularmoviesclean.movies.models.Movie

data class MovieItemViewModel(
    private val movie: Movie,
    private val itemPlaceHolder: MovieItemPlaceHolder
) {
    val imageUrl = movie.posterUrl
    val contentDescription = if (movie.title.isNotBlank()) movie.title else itemPlaceHolder.title
}

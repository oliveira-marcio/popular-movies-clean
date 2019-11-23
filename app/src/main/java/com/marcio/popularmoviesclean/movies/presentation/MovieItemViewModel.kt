package com.marcio.popularmoviesclean.movies.presentation

import com.marcio.popularmoviesclean.movies.models.Movie

data class MovieItemViewModel(
    private val movie: Movie,
    private val itemPlaceHolder: MovieItemPlaceHolder
) {
    val title = if (movie.title.isNotBlank()) movie.title else itemPlaceHolder.title
    val imageUrl = movie.poster
}

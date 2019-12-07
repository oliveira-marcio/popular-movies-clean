package com.marcio.popularmoviesclean.movies.presentation.main

import com.marcio.popularmoviesclean.movies.models.Movie

data class MovieItemViewModel(
    private val movie: Movie,
    private val itemPlaceHolder: MovieItemPlaceHolder
) {
    val id: String = movie.id
    val imageUrl: String = movie.posterUrl
    val contentDescription: String =
        if (movie.title.isNotBlank()) movie.title else itemPlaceHolder.title
}

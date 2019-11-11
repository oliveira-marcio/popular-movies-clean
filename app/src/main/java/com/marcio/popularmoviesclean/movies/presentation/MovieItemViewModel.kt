package com.marcio.popularmoviesclean.movies.presentation

import androidx.annotation.DrawableRes
import com.marcio.popularmoviesclean.movies.models.Movie

data class MovieItemViewModel(
    private val movie: Movie,
    @DrawableRes val imagePlaceHolderResource: Int
) {
    val title = movie.title
    val imageUrl = movie.poster
}

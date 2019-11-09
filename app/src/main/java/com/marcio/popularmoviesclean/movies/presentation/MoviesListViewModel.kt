package com.marcio.popularmoviesclean.movies.presentation

import androidx.annotation.DrawableRes
import com.marcio.popularmoviesclean.movies.models.Movies

data class MoviesListViewModel(
    private val movies: Movies,
    @DrawableRes private val imagePlaceHolderResource: Int
) {
    val list: List<MovieItemViewModel> = movies.list.map { movie ->
        MovieItemViewModel(movie, imagePlaceHolderResource)
    }
}

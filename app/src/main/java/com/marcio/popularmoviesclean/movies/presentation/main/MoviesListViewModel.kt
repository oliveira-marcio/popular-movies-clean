package com.marcio.popularmoviesclean.movies.presentation.main

import com.marcio.popularmoviesclean.movies.models.Movies

data class MoviesListViewModel(
    private val movies: Movies,
    private val itemPlaceHolder: MovieItemPlaceHolder
) {
    val list: List<MovieItemViewModel> = movies.list.map { movie ->
        MovieItemViewModel(
            movie,
            itemPlaceHolder
        )
    }
}

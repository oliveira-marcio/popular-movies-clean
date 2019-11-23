package com.marcio.popularmoviesclean.movies

import com.marcio.popularmoviesclean.movies.models.Movies

interface MoviesMainUseCases {
    fun loadMovies(category: Movies.Category = Movies.Category.POPULAR)
    fun loadMoreMovies()
}

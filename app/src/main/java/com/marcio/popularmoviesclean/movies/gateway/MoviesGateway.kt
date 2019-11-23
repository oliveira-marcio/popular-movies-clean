package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.movies.models.Movie
import com.marcio.popularmoviesclean.movies.models.Movies

interface MoviesGateway {
    fun getMovies(
        category: Movies.Category = Movies.Category.POPULAR,
        page: Int = 1
    ): List<Movie>
}

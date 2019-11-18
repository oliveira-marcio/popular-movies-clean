package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.movies.models.Movie

interface MoviesGateway {
    fun getPopularMovies(page: Int = 1): List<Movie>
    fun getTopRatedMovies(page: Int = 1): List<Movie>
}

package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.movies.models.Movie

interface MoviesGateway {
    fun getPopularMovies(): List<Movie>
    fun getTopRatedMovies(): List<Movie>
}

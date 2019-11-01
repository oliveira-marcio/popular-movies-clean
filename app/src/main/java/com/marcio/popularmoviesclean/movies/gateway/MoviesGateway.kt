package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.movies.models.Movie

interface MoviesGateway {
    fun getMovies(): List<Movie>
}

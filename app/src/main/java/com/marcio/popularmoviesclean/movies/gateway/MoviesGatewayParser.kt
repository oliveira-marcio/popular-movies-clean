package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.movies.models.Movie

interface MoviesGatewayParser {
    fun parseMovies(responseBody: String): List<Movie>
}

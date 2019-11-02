package com.marcio.popularmoviesclean.gateway

import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayParser
import com.marcio.popularmoviesclean.movies.models.Movie

class FakeMoviesGatewayJsonParser(
    private val moviesList: List<Movie> = emptyList()
) : MoviesGatewayParser {
    override fun parseMovies(responseBody: String) = moviesList
}

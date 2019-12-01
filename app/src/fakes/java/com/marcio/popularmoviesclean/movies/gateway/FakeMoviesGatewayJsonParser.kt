package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.movies.models.Movie

class FakeMoviesGatewayJsonParser(
    private val moviesList: List<Movie> = emptyList()
) : MoviesGatewayParser {
    override fun parseMovies(
        responseBody: String,
        posterBaseUrl: String,
        posterWidth: Int
    ): List<Movie> = moviesList
}

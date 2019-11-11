package com.marcio.popularmoviesclean.gateway

import com.marcio.popularmoviesclean.movies.gateway.MoviesGateway
import com.marcio.popularmoviesclean.movies.models.Movie

class FakeMoviesGateway(
    private val popularMovies: List<Movie> = emptyList(),
    private val topRatedMovies: List<Movie> = emptyList()
) : MoviesGateway {

    override fun getPopularMovies() = popularMovies

    override fun getTopRatedMovies() = topRatedMovies
}

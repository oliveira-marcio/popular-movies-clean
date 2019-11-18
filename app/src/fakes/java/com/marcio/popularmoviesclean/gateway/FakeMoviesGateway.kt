package com.marcio.popularmoviesclean.gateway

import com.marcio.popularmoviesclean.movies.gateway.MoviesGateway
import com.marcio.popularmoviesclean.movies.models.Movie

class FakeMoviesGateway(
    private val popularMovies: List<Movie> = emptyList(),
    private val topRatedMovies: List<Movie> = emptyList()
) : MoviesGateway {

    override fun getPopularMovies(page: Int) = if (page == 1) popularMovies else topRatedMovies

    override fun getTopRatedMovies(page: Int) = if (page == 1) topRatedMovies else popularMovies
}

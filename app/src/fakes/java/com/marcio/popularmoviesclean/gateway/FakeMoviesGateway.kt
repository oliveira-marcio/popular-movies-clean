package com.marcio.popularmoviesclean.gateway

import com.marcio.popularmoviesclean.movies.gateway.MoviesGateway
import com.marcio.popularmoviesclean.movies.models.Movie

class FakeMoviesGateway(private val movies: List<Movie> = emptyList()) : MoviesGateway {

    override fun getPopularMovies() = movies

    override fun getTopRatedMovies() = movies
}

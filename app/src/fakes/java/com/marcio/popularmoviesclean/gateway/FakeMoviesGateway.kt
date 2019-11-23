package com.marcio.popularmoviesclean.gateway

import com.marcio.popularmoviesclean.movies.gateway.MoviesGateway
import com.marcio.popularmoviesclean.movies.models.Movie
import com.marcio.popularmoviesclean.movies.models.Movies

class FakeMoviesGateway(
    popularMovies: List<Movie> = emptyList(),
    topRatedMovies: List<Movie> = emptyList()
) : MoviesGateway {

    private val moviesMap = mapOf(
        Movies.Category.POPULAR to listOf(popularMovies, topRatedMovies),
        Movies.Category.TOP_RATED to listOf(topRatedMovies, popularMovies)
    )

    override fun getMovies(category: Movies.Category, page: Int) =
        moviesMap.getValue(category)[page - 1]
}

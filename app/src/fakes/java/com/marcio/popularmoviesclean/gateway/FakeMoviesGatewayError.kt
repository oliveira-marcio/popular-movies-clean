package com.marcio.popularmoviesclean.gateway

import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError

class FakeMoviesGatewayError(private val networkError: Boolean = false) : MoviesGatewayError {
    override val isNetwork = networkError
}

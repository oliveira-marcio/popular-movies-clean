package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.state.ErrorFactory

class FakeMoviesGatewayErrorFactory(private val gatewayError: MoviesGatewayError? = null) :
    ErrorFactory<MoviesGatewayError> {
    override fun create(throwable: Throwable) = gatewayError!!
}

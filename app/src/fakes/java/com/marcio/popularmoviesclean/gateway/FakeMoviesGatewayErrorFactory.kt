package com.marcio.popularmoviesclean.gateway

import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError
import com.marcio.popularmoviesclean.state.ErrorFactory

class FakeMoviesGatewayErrorFactory(private val gatewayError: MoviesGatewayError? = null) :
    ErrorFactory<MoviesGatewayError> {
    override fun create(throwable: Throwable) = gatewayError!!
}

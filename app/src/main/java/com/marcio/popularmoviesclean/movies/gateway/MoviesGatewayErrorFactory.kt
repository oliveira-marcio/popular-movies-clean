package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.state.ErrorFactory

class MoviesGatewayErrorFactory : ErrorFactory<MoviesGatewayError> {
    override fun create(throwable: Throwable) = MoviesGatewayError(throwable)
}

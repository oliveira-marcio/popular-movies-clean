package com.marcio.popularmoviesclean.movies.gateway

import java.io.IOException

class MoviesGatewayError(cause: Throwable? = null) {
    val isNetwork: Boolean = cause != null && cause is IOException
}

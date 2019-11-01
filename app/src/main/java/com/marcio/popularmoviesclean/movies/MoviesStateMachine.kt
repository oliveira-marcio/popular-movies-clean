package com.marcio.popularmoviesclean.movies

import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.state.Dispatcher
import com.marcio.popularmoviesclean.state.ErrorFactory
import com.marcio.popularmoviesclean.state.StateMachine

abstract class MoviesStateMachine(
    dispatcher: Dispatcher<Movies>,
    errorFactory: ErrorFactory<MoviesGatewayError>
) : StateMachine<Movies, MoviesGatewayError>(dispatcher, errorFactory) {
    abstract fun setup()
}

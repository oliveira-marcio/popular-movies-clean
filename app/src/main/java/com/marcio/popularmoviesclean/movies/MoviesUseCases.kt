package com.marcio.popularmoviesclean.movies

import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError
import com.marcio.popularmoviesclean.movies.gateway.MoviesGateway
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.state.Dispatcher
import com.marcio.popularmoviesclean.state.ErrorFactory

class MoviesUseCases(
    private val moviesGateway: MoviesGateway,
    dispatcher: Dispatcher<Movies>,
    errorFactory: ErrorFactory<MoviesGatewayError>
) : MoviesStateMachine(dispatcher, errorFactory) {
    override fun setup() {
        loadNewState {
            Movies(moviesGateway.getMovies())
        }
    }
}

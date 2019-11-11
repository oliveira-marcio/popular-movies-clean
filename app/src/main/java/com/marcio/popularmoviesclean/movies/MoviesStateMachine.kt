package com.marcio.popularmoviesclean.movies

import com.marcio.popularmoviesclean.movies.gateway.MoviesGateway
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.state.Dispatcher
import com.marcio.popularmoviesclean.state.ErrorFactory
import com.marcio.popularmoviesclean.state.StateMachine

class MoviesStateMachine(
    private val moviesGateway: MoviesGateway,
    dispatcher: Dispatcher,
    errorFactory: ErrorFactory<MoviesGatewayError>
) : StateMachine<Movies, MoviesGatewayError>(dispatcher, errorFactory) {
    override fun start() {
        loadPopularMovies()
    }

    fun loadPopularMovies() {
        loadNewState {
            Movies(moviesGateway.getPopularMovies())
        }
    }

    fun loadTopRatedMovies() {
        loadNewState {
            Movies(moviesGateway.getTopRatedMovies())
        }
    }
}

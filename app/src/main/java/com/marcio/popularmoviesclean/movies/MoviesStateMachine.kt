package com.marcio.popularmoviesclean.movies

import com.marcio.popularmoviesclean.movies.gateway.MoviesGateway
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError
import com.marcio.popularmoviesclean.movies.models.Movie
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.state.Dispatcher
import com.marcio.popularmoviesclean.state.ErrorFactory
import com.marcio.popularmoviesclean.state.StateMachine

class MoviesStateMachine(
    private val moviesGateway: MoviesGateway,
    dispatcher: Dispatcher,
    errorFactory: ErrorFactory<MoviesGatewayError>
) : MoviesMainUseCases, StateMachine<Movies, MoviesGatewayError>(dispatcher, errorFactory) {

    private var currentPage = 1

    override fun start() {
        loadPopularMovies()
    }

    override fun loadPopularMovies() {
        loadMovies {
            moviesGateway.getPopularMovies()
        }
    }

    override fun loadTopRatedMovies() {
        loadMovies {
            moviesGateway.getTopRatedMovies()
        }
    }

    override fun loadMorePopularMovies() {
        loadMoreMovies {
            moviesGateway.getPopularMovies(++currentPage)
        }
    }

    override fun loadMoreTopRatedMovies() {
        loadMoreMovies {
            moviesGateway.getTopRatedMovies(++currentPage)
        }
    }

    private fun loadMovies(gateway: () -> List<Movie>) {
        loadNewState {
            currentPage = 1
            Movies(gateway.invoke())
        }
    }

    private fun loadMoreMovies(gateway: () -> List<Movie>) {
        loadNewState {
            val movies = currentState.value!!
            if (movies.list.isNotEmpty()) {
                val nextMovies = gateway.invoke()
                val moviesList = movies.list.toMutableList()
                moviesList.addAll(nextMovies)
                movies.copy(list = moviesList)
            } else {
                movies
            }
        }
    }
}

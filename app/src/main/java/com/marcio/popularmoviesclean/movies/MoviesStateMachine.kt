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
) : MoviesMainUseCases, StateMachine<Movies, MoviesGatewayError>(dispatcher, errorFactory) {

    private var currentPage = 1

    override fun start() {
        loadMovies()
    }

    override fun loadMovies(category: Movies.Category) {
        loadNewState {
            currentPage = 1
            Movies(moviesGateway.getMovies(category), category)
        }
    }

    override fun reloadMovies() {
        val movies = currentState.value
        if (movies == null) {
            loadMovies()
        } else {
            loadMovies(currentState.value!!.selectedCategory)
        }
    }

    override fun loadMoreMovies() {
        loadNewState {
            val movies = currentState.value!!
            if (movies.list.isNotEmpty()) {
                val nextMovies = moviesGateway.getMovies(movies.selectedCategory, ++currentPage)
                val moviesList = movies.list.toMutableList()
                moviesList.addAll(nextMovies)
                movies.copy(list = moviesList)
            } else {
                movies
            }
        }
    }

    override fun selectMovie(id: String) {
        loadNewState {
            currentState.value!!.copy(selectedMovieId = id)
        }
    }
}

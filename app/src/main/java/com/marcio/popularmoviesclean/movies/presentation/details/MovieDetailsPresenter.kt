package com.marcio.popularmoviesclean.movies.presentation.details

import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.state.Dispatcher
import com.marcio.popularmoviesclean.state.Presenter
import com.marcio.popularmoviesclean.state.State
import com.marcio.popularmoviesclean.state.StateListener

class MovieDetailsPresenter(
    private val mainDispatcher: Dispatcher
) : Presenter<MovieDetailsView, MovieDetailsPlaceHolder>(),
    StateListener<Movies, MoviesGatewayError> {

    private var currentState: State<Movies, MoviesGatewayError>? = null

    override fun onStateChanged(state: State<Movies, MoviesGatewayError>) {
        currentState = state
        updateView()
    }

    override fun updateView() {
        if (currentState == null) {
            return
        }

        mainDispatcher.dispatch {
            val movies = currentState!!.value
            if (currentState!!.name == State.Name.LOADED && movies!!.isSelected) {
                view?.showDetails(MovieDetailsViewModel(movies.selectedMovie!!, placeHolder!!))
            } else {
                navigator?.navigateBack()
            }
        }
    }
}

package com.marcio.popularmoviesclean.movies.presentation

import androidx.annotation.DrawableRes
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.state.Dispatcher
import com.marcio.popularmoviesclean.state.State
import com.marcio.popularmoviesclean.state.StateListener

class MoviesMainPresenter(
    private val mainDispatcher: Dispatcher,
    private val view: MoviesMainView,
    @DrawableRes private val imagePlaceHolderResource: Int
) : StateListener<Movies, MoviesGatewayError> {
    override fun onStateChanged(state: State<Movies, MoviesGatewayError>) {
        mainDispatcher.dispatch {
            when (state.name) {
                State.Name.IDLE, State.Name.LOADING -> {
                    if (state.value == null) {
                        view.hideMovies()
                    }
                    view.hideError()
                    view.showLoading()
                }

                State.Name.LOADED -> {
                    view.hideLoading()
                    view.hideError()
                    view.showMovies(MoviesListViewModel(state.value!!, imagePlaceHolderResource))
                }

                State.Name.ERROR -> {
                    view.hideLoading()
                    view.hideMovies()
                    if (state.error!!.isNetwork) {
                        view.showNetworkError()
                    } else {
                        view.showUnknownError()
                    }
                }
            }
        }
    }
}

package com.marcio.popularmoviesclean.movies.presentation

import androidx.annotation.DrawableRes
import com.marcio.popularmoviesclean.movies.MoviesMainUseCases
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.state.Dispatcher
import com.marcio.popularmoviesclean.state.Presenter
import com.marcio.popularmoviesclean.state.State
import com.marcio.popularmoviesclean.state.StateListener

class MoviesMainPresenter(
    private val moviesMainUseCases: MoviesMainUseCases,
    private val mainDispatcher: Dispatcher,
    @DrawableRes private val imagePlaceHolderResource: Int,
    private val listItemOffset: Int
) : Presenter<MoviesMainView>(), StateListener<Movies, MoviesGatewayError> {

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
            when (currentState!!.name) {
                State.Name.IDLE, State.Name.LOADING -> {
                    if (currentState!!.value == null) {
                        view?.hideMovies()
                    }
                    view?.hideError()
                    view?.showLoading()
                }

                State.Name.LOADED -> {
                    view?.hideLoading()
                    view?.hideError()
                    view?.showMovies(
                        MoviesListViewModel(
                            currentState!!.value!!,
                            imagePlaceHolderResource
                        )
                    )
                }

                State.Name.ERROR -> {
                    view?.hideLoading()
                    view?.hideMovies()
                    if (currentState!!.error!!.isNetwork) {
                        view?.showNetworkError()
                    } else {
                        view?.showUnknownError()
                    }
                }
            }
        }
    }

    fun onListScroll(canScrollDown: Boolean, itemCount: Int, lastVisibleItemPosition: Int) {
        if (canScrollDown) {
            val lastItemPosition = itemCount - 1
            if ((lastItemPosition - lastVisibleItemPosition) <= listItemOffset) {
                moviesMainUseCases.loadMoreMovies()
            }
        }
    }
}

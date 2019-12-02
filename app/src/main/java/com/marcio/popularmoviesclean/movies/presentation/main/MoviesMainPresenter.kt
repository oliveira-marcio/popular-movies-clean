package com.marcio.popularmoviesclean.movies.presentation.main

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
    private val listItemOffset: Int
) : Presenter<MoviesMainView, MovieItemPlaceHolder>(),
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
            when (currentState!!.name) {
                State.Name.IDLE, State.Name.LOADING -> {
                    view?.hideError()
                    if (currentState!!.value == null) {
                        view?.hideMovies()
                        view?.hideListLoading()
                        view?.showMainLoading()
                    } else {
                        view?.hideMainLoading()
                        view?.showListLoading()
                    }
                }

                State.Name.LOADED -> {
                    view?.hideMainLoading()
                    view?.hideListLoading()
                    view?.hideError()
                    view?.showMovies(
                        MoviesListViewModel(
                            currentState!!.value!!,
                            placeHolder!!
                        )
                    )
                }

                State.Name.ERROR -> {
                    val movies = currentState!!.value
                    view?.hideMainLoading()
                    view?.hideListLoading()
                    if (movies == null) {
                        view?.hideMovies()
                        if (currentState!!.error!!.isNetwork) {
                            view?.showNetworkError()
                        } else {
                            view?.showUnknownError()
                        }
                    } else {
                        view?.showMovies(
                            MoviesListViewModel(
                                movies,
                                placeHolder!!
                            )
                        )
                        if (currentState!!.error!!.isNetwork) {
                            view?.showNetworkWarning()
                        } else {
                            view?.showUnknownWarning()
                        }
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

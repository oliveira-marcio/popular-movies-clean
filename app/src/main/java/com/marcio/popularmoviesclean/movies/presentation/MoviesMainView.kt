package com.marcio.popularmoviesclean.movies.presentation

interface MoviesMainView {
    fun showMovies(listViewModel: MoviesListViewModel)
    fun hideMovies()
    fun showMainLoading()
    fun hideMainLoading()
    fun showListLoading()
    fun hideListLoading()
    fun showNetworkError()
    fun showUnknownError()
    fun hideError()
    fun showNetworkWarning()
    fun showUnknownWarning()
}

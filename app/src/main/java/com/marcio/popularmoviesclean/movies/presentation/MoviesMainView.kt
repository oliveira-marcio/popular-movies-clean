package com.marcio.popularmoviesclean.movies.presentation

interface MoviesMainView {
    fun showMovies(listViewModel: MoviesListViewModel)
    fun hideMovies()
    fun showLoading()
    fun hideLoading()
    fun showNetworkError()
    fun showUnknownError()
    fun hideError()
}

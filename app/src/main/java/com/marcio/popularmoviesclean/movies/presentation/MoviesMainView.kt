package com.marcio.popularmoviesclean.movies.presentation

interface MoviesMainView {
    fun showMovies(viewModel: MoviesListViewModel)
    fun hideMovies()
    fun showLoading()
    fun hideLoading()
    fun showNetworkError()
    fun showUnknownError()
    fun hideError()
}

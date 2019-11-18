package com.marcio.popularmoviesclean.movies

interface MoviesMainUseCases {
    fun loadPopularMovies()
    fun loadTopRatedMovies()
    fun loadMorePopularMovies()
    fun loadMoreTopRatedMovies()
}

package com.marcio.popularmoviesclean

import com.marcio.popularmoviesclean.movies.MoviesMainUseCases
import com.marcio.popularmoviesclean.movies.presentation.ImageLoader
import com.marcio.popularmoviesclean.movies.presentation.main.MoviesMainPresenter

interface DependencyManager {
    val moviesMainUseCases: MoviesMainUseCases
    val moviesMainPresenter: MoviesMainPresenter
    val imageLoader: ImageLoader
}

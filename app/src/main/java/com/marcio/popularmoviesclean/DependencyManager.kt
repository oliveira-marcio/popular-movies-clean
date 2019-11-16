package com.marcio.popularmoviesclean

import com.marcio.popularmoviesclean.movies.MoviesMainUseCases
import com.marcio.popularmoviesclean.movies.presentation.MoviesMainPresenter

interface DependencyManager {
    val moviesMainUseCases: MoviesMainUseCases
    val moviesMainPresenter: MoviesMainPresenter
}

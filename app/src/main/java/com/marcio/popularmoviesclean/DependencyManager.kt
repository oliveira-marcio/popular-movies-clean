package com.marcio.popularmoviesclean

import com.marcio.popularmoviesclean.movies.MoviesStateMachine
import com.marcio.popularmoviesclean.state.Dispatcher

interface DependencyManager {
    val mainDispatcher: Dispatcher
    val moviesStateMachine: MoviesStateMachine
}

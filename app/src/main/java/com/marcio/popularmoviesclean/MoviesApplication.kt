package com.marcio.popularmoviesclean

import com.marcio.popularmoviesclean.movies.MoviesUseCases
import com.marcio.popularmoviesclean.movies.gateway.MoviesGateway
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayErrorFactory
import com.marcio.popularmoviesclean.state.Dispatcher
import com.marcio.popularmoviesclean.state.DispatcherFactory

class MoviesApplication private constructor(
    lazyDispatcherFactory: Lazy<DispatcherFactory>,
    lazyMoviesGateway: Lazy<MoviesGateway>
) : DependencyManager {
    private val moviesUseCases: MoviesUseCases by lazy {
        MoviesUseCases(
            lazyMoviesGateway.value,
            lazyDispatcherFactory.value.createSerialDispatcher("Movies"),
            MoviesGatewayErrorFactory()
        )
    }

    private val mainDispatcher: Dispatcher by lazy {
        lazyDispatcherFactory.value.createMainDispatcher()
    }

    private fun setup() {
        moviesUseCases.setup()
    }

    class Builder {

        private var lazyDispatcherFactory: Lazy<DispatcherFactory>? = null
        private var lazyMoviesGateway: Lazy<MoviesGateway>? = null

        fun registerDispatcherFactory(lazyDispatcherFactory: Lazy<DispatcherFactory>): Builder {
            this.lazyDispatcherFactory = lazyDispatcherFactory
            return this
        }

        fun registerMoviesGateway(lazyConferenceGateway: Lazy<MoviesGateway>): Builder {
            this.lazyMoviesGateway = lazyConferenceGateway
            return this
        }

        fun setup(): MoviesApplication {
            val application =
                MoviesApplication(lazyDispatcherFactory!!, lazyMoviesGateway!!)
            application.setup()
            return application
        }
    }
}

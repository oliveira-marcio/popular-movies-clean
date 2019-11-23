package com.marcio.popularmoviesclean

import com.marcio.popularmoviesclean.movies.MoviesStateMachine
import com.marcio.popularmoviesclean.movies.gateway.MoviesGateway
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayErrorFactory
import com.marcio.popularmoviesclean.movies.presentation.MoviesMainPresenter
import com.marcio.popularmoviesclean.state.DispatcherFactory

class MoviesApplication private constructor(
    lazyDispatcherFactory: Lazy<DispatcherFactory>,
    lazyMoviesGateway: Lazy<MoviesGateway>
) : DependencyManager {

    override val moviesMainUseCases by lazy {
        moviesStateMachine
    }

    override val moviesMainPresenter by lazy {
        val presenter = MoviesMainPresenter(
            moviesStateMachine,
            lazyDispatcherFactory.value.createMainDispatcher(),
            BuildConfig.LIST_ITEM_OFFSET
        )
        moviesStateMachine.addStateChangedListener(presenter)
        presenter
    }

    private val moviesStateMachine: MoviesStateMachine by lazy {
        MoviesStateMachine(
            lazyMoviesGateway.value,
            lazyDispatcherFactory.value.createSerialDispatcher("Movies"),
            MoviesGatewayErrorFactory()
        )
    }

    private fun start() {
        moviesStateMachine.start()
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

        fun start(): MoviesApplication {
            val application =
                MoviesApplication(lazyDispatcherFactory!!, lazyMoviesGateway!!)
            application.start()
            return application
        }
    }
}

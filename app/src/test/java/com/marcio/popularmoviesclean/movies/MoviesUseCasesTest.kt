package com.marcio.popularmoviesclean.movies

import com.marcio.popularmoviesclean.TestData
import com.marcio.popularmoviesclean.gateway.FakeMoviesGateway
import com.marcio.popularmoviesclean.gateway.FakeMoviesGatewayErrorFactory
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.state.FakeDispatcher
import com.marcio.popularmoviesclean.state.State
import com.marcio.popularmoviesclean.state.StateListener
import io.mockk.mockk
import io.mockk.verifyOrder
import org.junit.Test

class MoviesUseCasesTest {
    @Test
    fun `Given movies gateway returns a list of movies When setup is called Then emit loaded state And the list of movies`() {

        val moviesList = TestData.MOVIES
        val moviesUseCases = MoviesUseCases(
            FakeMoviesGateway(moviesList),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesUseCases.addStateChangedListener(listenerMock)
        moviesUseCases.setup()

        verifyOrder {
            listenerMock.onStateChanged(State(State.Name.IDLE))
            listenerMock.onStateChanged(State(State.Name.LOADING))
            listenerMock.onStateChanged(State(State.Name.LOADED, Movies(moviesList)))
        }
    }
}

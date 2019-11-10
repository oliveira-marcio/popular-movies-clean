package com.marcio.popularmoviesclean.movies.presentation

import com.marcio.popularmoviesclean.TestData
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.state.FakeDispatcher
import com.marcio.popularmoviesclean.state.State
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.Test
import java.io.IOException

class MoviesMainPresenterTest {
    @Test
    fun `Given movies are loaded When state is updated Then show movies And hide main loading and error`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val presenter = MoviesMainPresenter(
            FakeDispatcher(),
            viewMock,
            123
        )
        val movies = Movies(TestData.MOVIES)

        presenter.onStateChanged(
            State(
                State.Name.LOADED,
                movies
            )
        )

        verifySequence {
            viewMock.hideLoading()
            viewMock.hideError()
            viewMock.showMovies(MoviesListViewModel(movies, 123))
        }
    }

    @Test
    fun `Given movies are loading And there are no movies When state is updated Then show main loading and hide other views`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val presenter = MoviesMainPresenter(
            FakeDispatcher(),
            viewMock,
            123
        )

        presenter.onStateChanged(
            State(State.Name.LOADING)
        )

        verifySequence {
            viewMock.hideMovies()
            viewMock.hideError()
            viewMock.showLoading()
        }
    }

    @Test
    fun `Given movies are loading And there are movies When state is updated Then show main loading and hide error and hide loading`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val presenter = MoviesMainPresenter(
            FakeDispatcher(),
            viewMock,
            123
        )
        val movies = Movies(TestData.MOVIES)

        presenter.onStateChanged(
            State(
                State.Name.LOADING,
                movies
            )
        )

        verifySequence {
            viewMock.hideError()
            viewMock.showLoading()
        }
    }

    @Test
    fun `Given a network error loading movies When state is updated Then show network error and hide other views`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val presenter = MoviesMainPresenter(
            FakeDispatcher(),
            viewMock,
            123
        )

        presenter.onStateChanged(
            State(State.Name.ERROR, error = MoviesGatewayError(IOException()))
        )

        verifySequence {
            viewMock.hideLoading()
            viewMock.hideMovies()
            viewMock.showNetworkError()
        }
    }

    @Test
    fun `Given a unknown error loading movies When state is updated Then show unknown error and hide other views`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val presenter = MoviesMainPresenter(
            FakeDispatcher(),
            viewMock,
            123
        )

        presenter.onStateChanged(
            State(State.Name.ERROR, error = MoviesGatewayError())
        )

        verifySequence {
            viewMock.hideLoading()
            viewMock.hideMovies()
            viewMock.showUnknownError()
        }
    }
}

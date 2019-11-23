package com.marcio.popularmoviesclean.movies.presentation

import com.marcio.popularmoviesclean.TestData
import com.marcio.popularmoviesclean.movies.MoviesMainUseCases
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.state.FakeDispatcher
import com.marcio.popularmoviesclean.state.State
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.Test
import java.io.IOException

class MoviesMainPresenterTest {
    @Test
    fun `Given movies are loaded When state is updated Then show movies And hide main loading and error`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val placeHolder = MovieItemPlaceHolder("", 123)
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )
        val movies = Movies(TestData.POPULAR_MOVIES)

        presenter.attachView(viewMock, placeHolder)
        presenter.onStateChanged(
            State(
                State.Name.LOADED,
                movies
            )
        )

        verifySequence {
            viewMock.hideLoading()
            viewMock.hideError()
            viewMock.showMovies(MoviesListViewModel(movies, placeHolder))
        }
    }

    @Test
    fun `Given movies are loaded When view is attached after state is updated Then show movies And hide main loading and error`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val placeHolder = MovieItemPlaceHolder("", 123)
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )
        val movies = Movies(TestData.POPULAR_MOVIES)

        presenter.onStateChanged(
            State(
                State.Name.LOADED,
                movies
            )
        )
        presenter.attachView(viewMock, placeHolder)

        verifySequence {
            viewMock.hideLoading()
            viewMock.hideError()
            viewMock.showMovies(MoviesListViewModel(movies, placeHolder))
        }
    }

    @Test
    fun `Given movies are loaded And there is no view attached When state is updated Then should do nothing`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )
        val movies = Movies(TestData.POPULAR_MOVIES)

        presenter.onStateChanged(
            State(
                State.Name.LOADED,
                movies
            )
        )

        verify(exactly = 0) {
            viewMock.showMovies(any())
            viewMock.hideMovies()
            viewMock.showLoading()
            viewMock.hideLoading()
            viewMock.showNetworkError()
            viewMock.showUnknownError()
            viewMock.hideError()
        }
    }

    @Test
    fun `Given state is null When view is attached Then show do nothing`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.attachView(viewMock, MovieItemPlaceHolder("", 123))

        verify(exactly = 0) {
            viewMock.showMovies(any())
            viewMock.hideMovies()
            viewMock.showLoading()
            viewMock.hideLoading()
            viewMock.showNetworkError()
            viewMock.showUnknownError()
            viewMock.hideError()
        }
    }

    @Test
    fun `Given movies are loading And there are no movies When state is updated Then show main loading and hide other views`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.attachView(viewMock, MovieItemPlaceHolder("", 123))
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
            mockk(),
            FakeDispatcher(),
            5
        )
        val movies = Movies(TestData.POPULAR_MOVIES)

        presenter.attachView(viewMock, MovieItemPlaceHolder("", 123))
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
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.attachView(viewMock, MovieItemPlaceHolder("", 123))
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
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.attachView(viewMock, MovieItemPlaceHolder("", 123))
        presenter.onStateChanged(
            State(State.Name.ERROR, error = MoviesGatewayError())
        )

        verifySequence {
            viewMock.hideLoading()
            viewMock.hideMovies()
            viewMock.showUnknownError()
        }
    }

    @Test
    fun `Given movies are loaded and can scroll down And last visible position is within offset When on scroll Then load more movies`() {
        val moviesMainUseCasesMock = mockk<MoviesMainUseCases>(relaxed = true)
        val presenter = MoviesMainPresenter(
            moviesMainUseCasesMock,
            FakeDispatcher(),
            5
        )

        presenter.onListScroll(true, 20, 16)
        verify {
            moviesMainUseCasesMock.loadMoreMovies()
        }
    }

    @Test
    fun `Given movies are loaded and can scroll down And last visible position is not within offset When on scroll Then do not load more movies`() {
        val moviesMainUseCasesMock = mockk<MoviesMainUseCases>(relaxed = true)
        val presenter = MoviesMainPresenter(
            moviesMainUseCasesMock,
            FakeDispatcher(),
            5
        )

        presenter.onListScroll(true, 20, 10)
        verify(exactly = 0) {
            moviesMainUseCasesMock.loadMoreMovies()
        }
    }

    @Test
    fun `Given movies are loaded and can not scroll down When on scroll Then not load more movies`() {
        val moviesMainUseCasesMock = mockk<MoviesMainUseCases>(relaxed = true)
        val presenter = MoviesMainPresenter(
            moviesMainUseCasesMock,
            FakeDispatcher(),
            5
        )

        presenter.onListScroll(false, 20, 16)
        verify(exactly = 0) {
            moviesMainUseCasesMock.loadMoreMovies()
        }
    }
}

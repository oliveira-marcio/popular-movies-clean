package com.marcio.popularmoviesclean.movies.presentation.main

import com.marcio.popularmoviesclean.TestData
import com.marcio.popularmoviesclean.movies.MoviesMainUseCases
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayError
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.state.FakeDispatcher
import com.marcio.popularmoviesclean.state.State
import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import java.io.IOException
import org.junit.Test

class MoviesMainPresenterTest {
    @Test
    fun `Given movies are loaded When state is updated Then show movies and hide main loading and error`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val movies = Movies(TestData.POPULAR_MOVIES)
        val placeHolder = MovieItemPlaceHolder()
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.attachView(viewMock, placeHolder)
        presenter.onStateChanged(State(State.Name.LOADED, movies))

        verifySequence {
            viewMock.hideMainLoading()
            viewMock.hideListLoading()
            viewMock.hideError()
            viewMock.showMovies(MoviesListViewModel(movies, placeHolder))
        }
    }

    @Test
    fun `Given movies are loaded When view is attached after state is updated Then show movies and hide main loading and error`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val movies = Movies(TestData.POPULAR_MOVIES)
        val placeHolder = MovieItemPlaceHolder()
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.onStateChanged(State(State.Name.LOADED, movies))
        presenter.attachView(viewMock, placeHolder)

        verifySequence {
            viewMock.hideMainLoading()
            viewMock.hideListLoading()
            viewMock.hideError()
            viewMock.showMovies(MoviesListViewModel(movies, placeHolder))
        }
    }

    @Test
    fun `Given movies are loaded and there is no view attached When state is updated Then should do nothing`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val movies = Movies(TestData.POPULAR_MOVIES)
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.onStateChanged(State(State.Name.LOADED, movies))

        verify {
            viewMock wasNot Called
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

        presenter.attachView(viewMock, MovieItemPlaceHolder())

        verify {
            viewMock wasNot Called
        }
    }

    @Test
    fun `Given movies are loading and there are no movies When state is updated Then show main loading and hide other views`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.attachView(viewMock, MovieItemPlaceHolder())
        presenter.onStateChanged(State(State.Name.LOADING))

        verifySequence {
            viewMock.hideError()
            viewMock.hideMovies()
            viewMock.hideListLoading()
            viewMock.showMainLoading()
        }
    }

    @Test
    fun `Given movies are loading and there are movies When state is updated Then show list loading and hide error and hide main loading`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val movies = Movies(TestData.POPULAR_MOVIES)
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.attachView(viewMock, MovieItemPlaceHolder())
        presenter.onStateChanged(State(State.Name.LOADING, movies))

        verifySequence {
            viewMock.hideError()
            viewMock.hideMainLoading()
            viewMock.showListLoading()
        }
    }

    @Test
    fun `Given a network error loading movies and there are no movies When state is updated Then show network error and hide other views`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.attachView(viewMock, MovieItemPlaceHolder())
        presenter.onStateChanged(State(State.Name.ERROR, error = MoviesGatewayError(IOException())))

        verifySequence {
            viewMock.hideMainLoading()
            viewMock.hideListLoading()
            viewMock.hideMovies()
            viewMock.showNetworkError()
        }
    }

    @Test
    fun `Given a network error loading movies and there are previous movies loaded When state is updated Then show movies list and show network warning and hide other views`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val movies = Movies(TestData.POPULAR_MOVIES)
        val placeHolder = MovieItemPlaceHolder()
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.attachView(viewMock, placeHolder)
        presenter.onStateChanged(
            State(
                State.Name.ERROR,
                Movies(TestData.POPULAR_MOVIES),
                error = MoviesGatewayError(IOException())
            )
        )

        verifySequence {
            viewMock.hideMainLoading()
            viewMock.hideListLoading()
            viewMock.showMovies(MoviesListViewModel(movies, placeHolder))
            viewMock.showNetworkWarning()
        }
    }

    @Test
    fun `Given a unknown error loading movies and there are no movies When state is updated Then show unknown error and hide other views`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.attachView(viewMock, MovieItemPlaceHolder())
        presenter.onStateChanged(State(State.Name.ERROR, error = MoviesGatewayError()))

        verifySequence {
            viewMock.hideMainLoading()
            viewMock.hideListLoading()
            viewMock.hideMovies()
            viewMock.showUnknownError()
        }
    }

    @Test
    fun `Given a unknown error loading movies and there are previous movies loaded When state is updated Then show movies list and show unknown warning and hide other views`() {
        val viewMock = mockk<MoviesMainView>(relaxed = true)
        val movies = Movies(TestData.POPULAR_MOVIES)
        val placeHolder = MovieItemPlaceHolder()
        val presenter = MoviesMainPresenter(
            mockk(),
            FakeDispatcher(),
            5
        )

        presenter.attachView(viewMock, placeHolder)
        presenter.onStateChanged(
            State(State.Name.ERROR, Movies(TestData.POPULAR_MOVIES), error = MoviesGatewayError())
        )

        verifySequence {
            viewMock.hideMainLoading()
            viewMock.hideListLoading()
            viewMock.showMovies(MoviesListViewModel(movies, placeHolder))
            viewMock.showUnknownWarning()
        }
    }

    @Test
    fun `Given movies are loaded and can scroll down and last visible position is within offset When on scroll Then load more movies`() {
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
    fun `Given movies are loaded and can scroll down and last visible position is not within offset When on scroll Then do not load more movies`() {
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

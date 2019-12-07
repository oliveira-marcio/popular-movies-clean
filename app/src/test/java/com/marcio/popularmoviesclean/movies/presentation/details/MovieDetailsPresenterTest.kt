package com.marcio.popularmoviesclean.movies.presentation.details

import com.marcio.popularmoviesclean.TestData
import com.marcio.popularmoviesclean.movies.models.Movies
import com.marcio.popularmoviesclean.navigation.Navigator
import com.marcio.popularmoviesclean.state.FakeDispatcher
import com.marcio.popularmoviesclean.state.State
import io.mockk.Called
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MovieDetailsPresenterTest {
    @Test
    fun `Given movie is selected When view is resumed Then show movie details`() {
        val viewMock = mockk<MovieDetailsView>(relaxed = true)
        val selectedMovie = TestData.POPULAR_MOVIES[0]
        val movies = Movies(TestData.POPULAR_MOVIES, selectedMovieId = selectedMovie.id)
        val placeHolder = MovieDetailsPlaceHolder()
        val presenter = MovieDetailsPresenter(FakeDispatcher())

        presenter.attachView(viewMock, placeHolder)
        presenter.onStateChanged(State(State.Name.LOADED, movies))

        verify {
            viewMock.showDetails(MovieDetailsViewModel(selectedMovie, placeHolder))
        }
    }

    @Test
    fun `Given movie is selected When view is attached after state is updated Then show movie details`() {
        val viewMock = mockk<MovieDetailsView>(relaxed = true)
        val selectedMovie = TestData.POPULAR_MOVIES[0]
        val movies = Movies(TestData.POPULAR_MOVIES, selectedMovieId = selectedMovie.id)
        val placeHolder = MovieDetailsPlaceHolder()
        val presenter = MovieDetailsPresenter(FakeDispatcher())

        presenter.onStateChanged(State(State.Name.LOADED, movies))
        presenter.attachView(viewMock, placeHolder)

        verify {
            viewMock.showDetails(MovieDetailsViewModel(selectedMovie, placeHolder))
        }
    }

    @Test
    fun `Given movies is selected and there is no view attached When state is updated Then should do nothing`() {
        val viewMock = mockk<MovieDetailsView>(relaxed = true)
        val selectedMovie = TestData.POPULAR_MOVIES[0]
        val movies = Movies(TestData.POPULAR_MOVIES, selectedMovieId = selectedMovie.id)
        val presenter = MovieDetailsPresenter(FakeDispatcher())

        presenter.onStateChanged(State(State.Name.LOADED, movies))

        verify {
            viewMock wasNot Called
        }
    }

    @Test
    fun `Given state is null When view is attached Then show do nothing`() {
        val viewMock = mockk<MovieDetailsView>(relaxed = true)
        val presenter = MovieDetailsPresenter(FakeDispatcher())

        presenter.attachView(viewMock, MovieDetailsPlaceHolder())

        verify {
            viewMock wasNot Called
        }
    }

    @Test
    fun `Given movie is not selected When view is resumed Then navigate back to main view`() {
        val viewMock = mockk<MovieDetailsView>(relaxed = true)
        val navigatorMock = mockk<Navigator>(relaxed = true)
        val movies = Movies(TestData.POPULAR_MOVIES)
        val placeHolder = MovieDetailsPlaceHolder()
        val presenter = MovieDetailsPresenter(FakeDispatcher())

        presenter.attachView(viewMock, placeHolder)
        presenter.attachNavigator(navigatorMock)
        presenter.onStateChanged(State(State.Name.LOADED, movies))

        verify {
            navigatorMock.navigateBack()
        }

        verify {
            viewMock wasNot Called
        }
    }

    @Test
    fun `Given an idle state When view is resumed Then navigate back to main view`() {
        val viewMock = mockk<MovieDetailsView>(relaxed = true)
        val navigatorMock = mockk<Navigator>(relaxed = true)
        val placeHolder = MovieDetailsPlaceHolder()
        val presenter = MovieDetailsPresenter(FakeDispatcher())

        presenter.attachView(viewMock, placeHolder)
        presenter.attachNavigator(navigatorMock)
        presenter.onStateChanged(State(State.Name.IDLE))

        verify {
            navigatorMock.navigateBack()
        }

        verify {
            viewMock wasNot Called
        }
    }

    @Test
    fun `Given movies are loading When view is resumed Then navigate back to main view`() {
        val viewMock = mockk<MovieDetailsView>(relaxed = true)
        val navigatorMock = mockk<Navigator>(relaxed = true)
        val placeHolder = MovieDetailsPlaceHolder()
        val presenter = MovieDetailsPresenter(FakeDispatcher())

        presenter.attachView(viewMock, placeHolder)
        presenter.attachNavigator(navigatorMock)
        presenter.onStateChanged(State(State.Name.LOADING))

        verify {
            navigatorMock.navigateBack()
        }

        verify {
            viewMock wasNot Called
        }
    }

    @Test
    fun `Given error loading movies When view is resumed Then navigate back to main view`() {
        val viewMock = mockk<MovieDetailsView>(relaxed = true)
        val navigatorMock = mockk<Navigator>(relaxed = true)
        val placeHolder = MovieDetailsPlaceHolder()
        val presenter = MovieDetailsPresenter(FakeDispatcher())

        presenter.attachView(viewMock, placeHolder)
        presenter.attachNavigator(navigatorMock)
        presenter.onStateChanged(State(State.Name.ERROR))

        verify {
            navigatorMock.navigateBack()
        }

        verify {
            viewMock wasNot Called
        }
    }
}

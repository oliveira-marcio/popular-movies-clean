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

class MoviesStateMachineTest {
    @Test
    fun `Given movies gateway returns a list of movies When start is called Then emit loaded state And the list of movies`() {

        val popularMoviesList = TestData.POPULAR_MOVIES
        val moviesStateMachine = MoviesStateMachine(
            FakeMoviesGateway(popularMoviesList),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesStateMachine.addStateChangedListener(listenerMock)
        moviesStateMachine.start()

        verifyOrder {
            listenerMock.onStateChanged(State(State.Name.IDLE))
            listenerMock.onStateChanged(State(State.Name.LOADING))
            listenerMock.onStateChanged(State(State.Name.LOADED, Movies(popularMoviesList)))
        }
    }

    @Test
    fun `Given movies gateway returns a list of movies When load popular movies is called Then emit loaded state And the list of movies`() {

        val popularMoviesList = TestData.POPULAR_MOVIES
        val moviesStateMachine = MoviesStateMachine(
            FakeMoviesGateway(popularMoviesList),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesStateMachine.addStateChangedListener(listenerMock)
        moviesStateMachine.start()
        moviesStateMachine.loadPopularMovies()

        verifyOrder {
            listenerMock.onStateChanged(State(State.Name.LOADED, Movies(popularMoviesList)))
            listenerMock.onStateChanged(State(State.Name.LOADING, Movies(popularMoviesList)))
            listenerMock.onStateChanged(State(State.Name.LOADED, Movies(popularMoviesList)))
        }
    }

    @Test
    fun `Given movies gateway returns a list of movies When load top rated movies is called Then emit loaded state And the list of movies`() {

        val popularMoviesList = TestData.POPULAR_MOVIES
        val topRatedMoviesList = TestData.TOP_RATED_MOVIES
        val moviesStateMachine = MoviesStateMachine(
            FakeMoviesGateway(popularMoviesList, topRatedMoviesList),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesStateMachine.addStateChangedListener(listenerMock)
        moviesStateMachine.start()
        moviesStateMachine.loadTopRatedMovies()

        verifyOrder {
            listenerMock.onStateChanged(State(State.Name.LOADED, Movies(popularMoviesList)))
            listenerMock.onStateChanged(State(State.Name.LOADING, Movies(popularMoviesList)))
            listenerMock.onStateChanged(State(State.Name.LOADED, Movies(topRatedMoviesList)))
        }
    }

    @Test
    fun `Given movies gateway returns the next list of popular movies When load more popular movies is called Then emit list of popular movies plus the next movies`() {

        val popularMoviesList = TestData.POPULAR_MOVIES
        val topRatedMoviesList = TestData.TOP_RATED_MOVIES
        val allMoviesList = popularMoviesList + topRatedMoviesList
        val moviesStateMachine = MoviesStateMachine(
            FakeMoviesGateway(popularMoviesList, topRatedMoviesList),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesStateMachine.addStateChangedListener(listenerMock)
        moviesStateMachine.loadPopularMovies()
        moviesStateMachine.loadMorePopularMovies()

        verifyOrder {
            listenerMock.onStateChanged(State(State.Name.LOADED, Movies(popularMoviesList)))
            listenerMock.onStateChanged(State(State.Name.LOADING, Movies(popularMoviesList)))
            listenerMock.onStateChanged(State(State.Name.LOADED, Movies(allMoviesList)))
        }
    }

    @Test
    fun `Given movies gateway returns the next list of top rated movies When load more top rated movies is called Then emit list of top rated movies plus the next movies`() {

        val popularMoviesList = TestData.POPULAR_MOVIES
        val topRatedMoviesList = TestData.TOP_RATED_MOVIES
        val allMoviesList = topRatedMoviesList + popularMoviesList
        val moviesStateMachine = MoviesStateMachine(
            FakeMoviesGateway(popularMoviesList, topRatedMoviesList),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesStateMachine.addStateChangedListener(listenerMock)
        moviesStateMachine.loadTopRatedMovies()
        moviesStateMachine.loadMoreTopRatedMovies()

        verifyOrder {
            listenerMock.onStateChanged(State(State.Name.LOADED, Movies(topRatedMoviesList)))
            listenerMock.onStateChanged(State(State.Name.LOADING, Movies(topRatedMoviesList)))
            listenerMock.onStateChanged(State(State.Name.LOADED, Movies(allMoviesList)))
        }
    }
}

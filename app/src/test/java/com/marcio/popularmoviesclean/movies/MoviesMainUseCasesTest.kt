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

class MoviesMainUseCasesTest {
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
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(popularMoviesList, Movies.Category.POPULAR)
                )
            )
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
        moviesStateMachine.loadMovies()

        verifyOrder {
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(popularMoviesList, Movies.Category.POPULAR)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADING,
                    Movies(popularMoviesList, Movies.Category.POPULAR)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(popularMoviesList, Movies.Category.POPULAR)
                )
            )
        }
    }

    @Test
    fun `Given movies gateway returns a list of movies When load top rated movies is called Then emit loaded state And the list of movies`() {

        val topRatedMoviesList = TestData.TOP_RATED_MOVIES
        val moviesStateMachine = MoviesStateMachine(
            FakeMoviesGateway(topRatedMovies = topRatedMoviesList),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesStateMachine.addStateChangedListener(listenerMock)
        moviesStateMachine.start()
        moviesStateMachine.loadMovies(Movies.Category.TOP_RATED)

        verifyOrder {
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(emptyList(), Movies.Category.POPULAR)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADING,
                    Movies(emptyList(), Movies.Category.POPULAR)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(topRatedMoviesList, Movies.Category.TOP_RATED)
                )
            )
        }
    }

    @Test
    fun `Given current category is top rated And movies gateway returns a list of movies When reload movies is called Then emit list of top rated movies`() {

        val topRatedMoviesList = TestData.TOP_RATED_MOVIES
        val moviesStateMachine = MoviesStateMachine(
            FakeMoviesGateway(topRatedMovies = topRatedMoviesList),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesStateMachine.addStateChangedListener(listenerMock)
        moviesStateMachine.start()
        moviesStateMachine.loadMovies(Movies.Category.TOP_RATED)
        moviesStateMachine.reloadMovies()

        verifyOrder {
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(topRatedMoviesList, Movies.Category.TOP_RATED)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADING,
                    Movies(topRatedMoviesList, Movies.Category.TOP_RATED)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(topRatedMoviesList, Movies.Category.TOP_RATED)
                )
            )
        }
    }

    @Test
    fun `Given current category is popular And movies gateway returns a list of movies When reload movies is called Then emit list of popular movies`() {

        val popularMoviesList = TestData.POPULAR_MOVIES
        val moviesStateMachine = MoviesStateMachine(
            FakeMoviesGateway(popularMoviesList),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesStateMachine.addStateChangedListener(listenerMock)
        moviesStateMachine.start()
        moviesStateMachine.reloadMovies()

        verifyOrder {
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(popularMoviesList, Movies.Category.POPULAR)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADING,
                    Movies(popularMoviesList, Movies.Category.POPULAR)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(popularMoviesList, Movies.Category.POPULAR)
                )
            )
        }
    }

    @Test
    fun `Given current state is null When reload movies is called Then emit list of popular movies`() {

        val popularMoviesList = TestData.POPULAR_MOVIES
        val moviesStateMachine = MoviesStateMachine(
            FakeMoviesGateway(popularMoviesList),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesStateMachine.addStateChangedListener(listenerMock)
        moviesStateMachine.reloadMovies()

        verifyOrder {
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(popularMoviesList, Movies.Category.POPULAR)
                )
            )
        }
    }

    @Test
    fun `Given movies gateway returns the next list of popular movies When load more movies is called Then emit list of popular movies plus the next movies`() {

        val firstMoviesList = TestData.POPULAR_MOVIES
        val secondMoviesList = TestData.TOP_RATED_MOVIES
        val allMoviesList = firstMoviesList + secondMoviesList
        val moviesStateMachine = MoviesStateMachine(
            FakeMoviesGateway(firstMoviesList, secondMoviesList),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesStateMachine.addStateChangedListener(listenerMock)
        moviesStateMachine.loadMovies()
        moviesStateMachine.loadMoreMovies()

        verifyOrder {
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(firstMoviesList, Movies.Category.POPULAR)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADING,
                    Movies(firstMoviesList, Movies.Category.POPULAR)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(allMoviesList, Movies.Category.POPULAR)
                )
            )
        }
    }

    @Test
    fun `Given movies gateway returns the next list of top rated movies When load more movies is called Then emit list of top rated movies plus the next movies`() {

        val firstMoviesList = TestData.TOP_RATED_MOVIES
        val secondMoviesList = TestData.POPULAR_MOVIES
        val allMoviesList = firstMoviesList + secondMoviesList
        val moviesStateMachine = MoviesStateMachine(
            FakeMoviesGateway(secondMoviesList, firstMoviesList),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesStateMachine.addStateChangedListener(listenerMock)
        moviesStateMachine.loadMovies(Movies.Category.TOP_RATED)
        moviesStateMachine.loadMoreMovies()

        verifyOrder {
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(firstMoviesList, Movies.Category.TOP_RATED)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADING,
                    Movies(firstMoviesList, Movies.Category.TOP_RATED)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(allMoviesList, Movies.Category.TOP_RATED)
                )
            )
        }
    }

    @Test
    fun `Given movies list is empty When load more movies is called Then emit empty list`() {

        val moviesStateMachine = MoviesStateMachine(
            FakeMoviesGateway(),
            FakeDispatcher(),
            FakeMoviesGatewayErrorFactory()
        )

        val listenerMock = mockk<StateListener<Movies, MoviesGatewayError>>(relaxed = true)
        moviesStateMachine.addStateChangedListener(listenerMock)
        moviesStateMachine.loadMovies()
        moviesStateMachine.loadMoreMovies()

        verifyOrder {
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(emptyList(), Movies.Category.POPULAR)
                )
            )
            listenerMock.onStateChanged(
                State(
                    State.Name.LOADED,
                    Movies(emptyList(), Movies.Category.POPULAR)
                )
            )
        }
    }
}

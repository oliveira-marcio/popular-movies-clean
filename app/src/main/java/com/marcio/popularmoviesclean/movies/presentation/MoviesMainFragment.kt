package com.marcio.popularmoviesclean.movies.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcio.popularmoviesclean.DependencyManager
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.android.ViewContainer
import kotlinx.android.synthetic.main.fragment_movies_main.errorView
import kotlinx.android.synthetic.main.fragment_movies_main.loadingBar
import kotlinx.android.synthetic.main.fragment_movies_main.moviesList

class MoviesMainFragment : Fragment(), MoviesMainView {

    companion object {
        const val LOAD_POPULAR_MOVIES = "LOAD_POPULAR_MOVIES"
    }

    private var dependencyManager: DependencyManager? = null

    private var adapter: MoviesListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    private val moviesMainUseCases by lazy {
        dependencyManager!!.moviesMainUseCases
    }

    private val moviesMainPresenter by lazy {
        dependencyManager!!.moviesMainPresenter
    }

    private var loadPopularMovies: Boolean = true

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(LOAD_POPULAR_MOVIES, loadPopularMovies)
        super.onSaveInstanceState(outState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dependencyManager = (context as ViewContainer).dependencyManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_movies_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            loadPopularMovies = savedInstanceState.getBoolean(LOAD_POPULAR_MOVIES)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = MoviesListAdapter(
            LayoutInflater.from(context)
        )
        layoutManager = LinearLayoutManager(context)
        moviesList.layoutManager = layoutManager
        moviesList.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        moviesMainPresenter.attachView(this)
    }

    override fun onPause() {
        moviesMainPresenter.detachView()
        super.onPause()
    }

    override fun onDestroyView() {
        adapter = null
        layoutManager = null
        super.onDestroyView()
    }

    override fun onDetach() {
        dependencyManager = null
        super.onDetach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionRefresh -> {
                if (loadPopularMovies) {
                    moviesMainUseCases.loadPopularMovies()
                } else {
                    moviesMainUseCases.loadTopRatedMovies()
                }
                return true
            }
            R.id.actionPopular -> {
                loadPopularMovies = true
                moviesMainUseCases.loadPopularMovies()
                return true
            }
            R.id.actionTopRated -> {
                loadPopularMovies = false
                moviesMainUseCases.loadTopRatedMovies()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showMovies(listViewModel: MoviesListViewModel) {
        moviesList.visibility = View.VISIBLE
        adapter!!.setMovies(listViewModel.list)
    }

    override fun hideMovies() {
        moviesList.visibility = View.GONE
    }

    override fun showLoading() {
        loadingBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingBar.visibility = View.GONE
    }

    override fun showNetworkError() {
        errorView.text = getString(R.string.error_internet)
        errorView.visibility = View.VISIBLE
    }

    override fun showUnknownError() {
        errorView.text = getString(R.string.error_unknown)
        errorView.visibility = View.VISIBLE
    }

    override fun hideError() {
        errorView.visibility = View.GONE
    }
}

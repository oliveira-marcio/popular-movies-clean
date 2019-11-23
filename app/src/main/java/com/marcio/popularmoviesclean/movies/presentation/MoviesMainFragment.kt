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
import androidx.recyclerview.widget.RecyclerView
import com.marcio.popularmoviesclean.DependencyManager
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.android.ViewContainer
import com.marcio.popularmoviesclean.movies.models.Movies
import kotlinx.android.synthetic.main.fragment_movies_main.errorView
import kotlinx.android.synthetic.main.fragment_movies_main.loadingBar
import kotlinx.android.synthetic.main.fragment_movies_main.moviesList

class MoviesMainFragment : Fragment(), MoviesMainView {

    private var dependencyManager: DependencyManager? = null

    private var adapter: MoviesListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private var scrollListener: RecyclerView.OnScrollListener? = null

    private val moviesMainUseCases by lazy {
        dependencyManager!!.moviesMainUseCases
    }

    private val moviesMainPresenter by lazy {
        dependencyManager!!.moviesMainPresenter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dependencyManager = (context as ViewContainer).dependencyManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                moviesMainPresenter.onListScroll(
                    recyclerView.canScrollVertically(1),
                    adapter!!.itemCount,
                    layoutManager!!.findLastVisibleItemPosition()
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_movies_main, container, false)
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
        moviesMainPresenter.attachView(
            this,
            MovieItemPlaceHolder(
                getString(R.string.movie_title_placeholder),
                -1 // TODO: Should be a real resource
            )
        )
        moviesList.addOnScrollListener(scrollListener!!)
    }

    override fun onPause() {
        moviesMainPresenter.detachView()
        moviesList.removeOnScrollListener(scrollListener!!)
        super.onPause()
    }

    override fun onDestroyView() {
        adapter = null
        layoutManager = null
        moviesList.removeOnScrollListener(scrollListener!!)
        super.onDestroyView()
    }

    override fun onDestroy() {
        scrollListener = null
        super.onDestroy()
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
                moviesMainUseCases.reloadMovies()
                return true
            }
            R.id.actionPopular -> {
                moviesMainUseCases.loadMovies()
                return true
            }
            R.id.actionTopRated -> {
                moviesMainUseCases.loadMovies(Movies.Category.TOP_RATED)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showMovies(listViewModel: MoviesListViewModel) {
        moviesList.visibility = View.VISIBLE
        val viewsState = layoutManager!!.onSaveInstanceState()
        adapter!!.setMovies(listViewModel.list)
        layoutManager!!.onRestoreInstanceState(viewsState)
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

package com.marcio.popularmoviesclean.movies.presentation.main

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcio.popularmoviesclean.DependencyManager
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.android.ViewContainer
import com.marcio.popularmoviesclean.movies.models.Movies
import kotlin.math.roundToInt
import kotlinx.android.synthetic.main.fragment_movies_main.errorView
import kotlinx.android.synthetic.main.fragment_movies_main.listLoading
import kotlinx.android.synthetic.main.fragment_movies_main.mainLoading
import kotlinx.android.synthetic.main.fragment_movies_main.moviesGrid

class MoviesMainFragment : Fragment(),
    MoviesMainView {

    companion object {
        const val IMAGE_LOADER_REFERENCE = "MoviesMainFragment"
    }

    private var dependencyManager: DependencyManager? = null

    private var adapter: MoviesListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private var scrollListener: RecyclerView.OnScrollListener? = null
    private var toast: Toast? = null
    private var display: Display? = null

    private val moviesMainUseCases by lazy {
        dependencyManager!!.moviesMainUseCases
    }

    private val moviesMainPresenter by lazy {
        dependencyManager!!.moviesMainPresenter
    }

    private val imageLoader by lazy {
        dependencyManager!!.imageLoader
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
                if (dy > 0) {
                    moviesMainPresenter.onListScroll(
                        recyclerView.canScrollVertically(1),
                        adapter!!.itemCount,
                        layoutManager!!.findLastVisibleItemPosition()
                    )
                }
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

        display = activity!!.windowManager.defaultDisplay
        val spanCount =
            calculateBestSpanCount(display!!, resources.getInteger(R.integer.thumb_size))
        layoutManager = GridLayoutManager(context, spanCount)
        moviesGrid.layoutManager = layoutManager

        adapter =
            MoviesListAdapter(
                LayoutInflater.from(context),
                imageLoader,
                IMAGE_LOADER_REFERENCE
            )
        moviesGrid.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        moviesMainPresenter.attachView(
            this,
            MovieItemPlaceHolder(
                getString(R.string.movie_title_placeholder)
            )
        )
        moviesGrid.addOnScrollListener(scrollListener!!)
    }

    override fun onPause() {
        moviesMainPresenter.detachView()
        moviesGrid.removeOnScrollListener(scrollListener!!)
        super.onPause()
    }

    override fun onDestroyView() {
        adapter = null
        layoutManager = null
        toast = null
        display = null
        imageLoader.cancel(IMAGE_LOADER_REFERENCE)
        moviesGrid.removeOnScrollListener(scrollListener!!)
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
        moviesGrid.visibility = View.VISIBLE
        val viewsState = layoutManager!!.onSaveInstanceState()
        adapter!!.setMovies(listViewModel.list)
        layoutManager!!.onRestoreInstanceState(viewsState)
    }

    override fun hideMovies() {
        moviesGrid.visibility = View.GONE
    }

    override fun showMainLoading() {
        mainLoading.visibility = View.VISIBLE
    }

    override fun hideMainLoading() {
        mainLoading.visibility = View.GONE
    }

    override fun showListLoading() {
        listLoading.visibility = View.VISIBLE
    }

    override fun hideListLoading() {
        listLoading.visibility = View.GONE
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

    override fun showNetworkWarning() {
        showToast(getString(R.string.error_internet))
    }

    override fun showUnknownWarning() {
        showToast(getString(R.string.error_unknown))
    }

    private fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    private fun calculateBestSpanCount(display: Display, posterWidth: Int): Int {
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val screenWidth = outMetrics.widthPixels.toFloat()
        return (screenWidth / posterWidth).roundToInt()
    }
}

package com.marcio.popularmoviesclean.movies.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
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

    private var dependencyManager: DependencyManager? = null

    private var adapter: MoviesListAdapter? = null
    private var layoutManager: LinearLayoutManager? = null

    private val moviesMainPresenter by lazy {
        dependencyManager!!.moviesMainPresenter
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

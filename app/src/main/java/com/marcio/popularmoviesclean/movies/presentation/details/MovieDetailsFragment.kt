package com.marcio.popularmoviesclean.movies.presentation.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marcio.popularmoviesclean.DependencyManager
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.android.ViewContainer
import com.marcio.popularmoviesclean.navigation.Navigator
import kotlinx.android.synthetic.main.fragment_movies_details.averageRatingView
import kotlinx.android.synthetic.main.fragment_movies_details.posterView
import kotlinx.android.synthetic.main.fragment_movies_details.releaseDateView
import kotlinx.android.synthetic.main.fragment_movies_details.synopsisView
import kotlinx.android.synthetic.main.fragment_movies_details.titleView
import kotlinx.android.synthetic.main.fragment_movies_details.userRatingView

class MovieDetailsFragment : Fragment(),
    MovieDetailsView {

    private var dependencyManager: DependencyManager? = null
    private var navigator: Navigator? = null

    private val movieDetailsPresenter by lazy {
        dependencyManager!!.movieDetailsPresenter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dependencyManager = (context as ViewContainer).dependencyManager
        navigator = (context as ViewContainer).navigator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onResume() {
        super.onResume()
        movieDetailsPresenter.attachView(
            this,
            MovieDetailsPlaceHolder()
        )
        movieDetailsPresenter.attachNavigator(navigator!!)
    }

    override fun onPause() {
        movieDetailsPresenter.detachView()
        movieDetailsPresenter.detachNavigator()
        super.onPause()
    }

    override fun onDetach() {
        dependencyManager = null
        navigator = null
        super.onDetach()
    }

    override fun showDetails(detailsViewModel: MovieDetailsViewModel) {
        titleView.text = detailsViewModel.title
        releaseDateView.text = detailsViewModel.releaseDate
        averageRatingView.text = detailsViewModel.averageRating
        userRatingView.text = detailsViewModel.ratingsCount
        synopsisView.text = detailsViewModel.synopsis

        posterView.contentDescription = detailsViewModel.title
    }
}

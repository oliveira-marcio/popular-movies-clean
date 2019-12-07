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
import kotlinx.android.synthetic.main.fragment_movies_details.toolbar
import kotlinx.android.synthetic.main.fragment_movies_details.userRatingView

class MovieDetailsFragment : Fragment(),
    MovieDetailsView {

    companion object {
        const val IMAGE_LOADER_REFERENCE = "MovieDetailsFragment"
    }

    private var dependencyManager: DependencyManager? = null
    private var navigator: Navigator? = null
    private var backNavigationListener: View.OnClickListener? = null

    private val movieDetailsPresenter by lazy {
        dependencyManager!!.movieDetailsPresenter
    }

    private val imageLoader by lazy {
        dependencyManager!!.imageLoader
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dependencyManager = (context as ViewContainer).dependencyManager
        navigator = (context as ViewContainer).navigator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backNavigationListener = View.OnClickListener { navigator!!.navigateBack() }
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
            MovieDetailsPlaceHolder(
                getString(R.string.movie_title_placeholder),
                getString(R.string.movie_release_date_placeholder),
                getString(R.string.movie_average_ratings_placeholder),
                getString(R.string.movie_rating_count_placeholder),
                getString(R.string.movie_synopsis_placeholder)
            )
        )
        movieDetailsPresenter.attachNavigator(navigator!!)
        toolbar.setNavigationOnClickListener(backNavigationListener)
    }

    override fun onPause() {
        movieDetailsPresenter.detachView()
        movieDetailsPresenter.detachNavigator()
        toolbar.setNavigationOnClickListener(null)
        super.onPause()
    }

    override fun onDestroyView() {
        imageLoader.cancel(IMAGE_LOADER_REFERENCE)
        super.onDestroyView()
    }

    override fun onDetach() {
        dependencyManager = null
        navigator = null
        backNavigationListener = null
        super.onDetach()
    }

    override fun showDetails(detailsViewModel: MovieDetailsViewModel) {
        toolbar.title = detailsViewModel.title
        titleView.text = detailsViewModel.title
        releaseDateView.text = detailsViewModel.releaseDate
        averageRatingView.text = detailsViewModel.averageRating
        userRatingView.text = detailsViewModel.ratingsCount
        synopsisView.text = detailsViewModel.synopsis

        posterView.contentDescription = detailsViewModel.title
        imageLoader.load(posterView, detailsViewModel.posterUrl, IMAGE_LOADER_REFERENCE)
    }
}

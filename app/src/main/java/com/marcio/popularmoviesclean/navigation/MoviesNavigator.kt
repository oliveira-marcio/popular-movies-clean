package com.marcio.popularmoviesclean.navigation

import androidx.fragment.app.FragmentManager
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.movies.presentation.details.MovieDetailsFragment
import com.marcio.popularmoviesclean.movies.presentation.main.MoviesMainFragment

class MoviesNavigator(
    private val fragmentManager: FragmentManager
) : Navigator {
    override fun navigateToMainView() {
        fragmentManager.beginTransaction()
            .replace(
                R.id.activity_container,
                MoviesMainFragment()
            )
            .commit()
    }

    override fun navigateToDetailsView() {
        fragmentManager.beginTransaction()
            .replace(
                R.id.activity_container,
                MovieDetailsFragment()
            )
            .addToBackStack("MoviesList->MovieDetail")
            .commit()
    }

    override fun navigateBack() {
        fragmentManager.popBackStack()
    }
}

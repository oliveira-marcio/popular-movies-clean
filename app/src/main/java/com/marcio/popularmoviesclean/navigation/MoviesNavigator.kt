package com.marcio.popularmoviesclean.navigation

import androidx.fragment.app.FragmentManager
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.movies.presentation.MoviesMainFragment

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
}

package com.marcio.popularmoviesclean.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcio.popularmoviesclean.DependencyManager
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.movies.presentation.MoviesMainFragment

class MainActivity : AppCompatActivity(), ViewContainer {

    override val dependencyManager: DependencyManager by lazy {
        (application as AndroidApplication).dependencyManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.activity_container,
                    MoviesMainFragment()
                )
                .commit()
        }
    }
}

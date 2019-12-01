package com.marcio.popularmoviesclean.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marcio.popularmoviesclean.DependencyManager
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.navigation.MoviesNavigator
import com.marcio.popularmoviesclean.navigation.Navigator

class MainActivity : AppCompatActivity(), ViewContainer {

    override val dependencyManager: DependencyManager by lazy {
        (application as AndroidApplication).dependencyManager
    }

    override val navigator: Navigator by lazy {
        MoviesNavigator(supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigator.navigateToMainView()
        }
    }
}

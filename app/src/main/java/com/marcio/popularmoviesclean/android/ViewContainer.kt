package com.marcio.popularmoviesclean.android

import com.marcio.popularmoviesclean.DependencyManager
import com.marcio.popularmoviesclean.navigation.Navigator

interface ViewContainer {
    val dependencyManager: DependencyManager
    val navigator: Navigator
}

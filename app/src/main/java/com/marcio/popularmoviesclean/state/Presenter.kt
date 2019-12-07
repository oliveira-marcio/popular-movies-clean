package com.marcio.popularmoviesclean.state

import com.marcio.popularmoviesclean.navigation.Navigator

abstract class Presenter<V, P> {

    protected var view: V? = null
    protected var placeHolder: P? = null
    protected var navigator: Navigator? = null

    protected abstract fun updateView()

    fun attachView(view: V, placeHolder: P? = null) {
        this.view = view
        this.placeHolder = placeHolder
        updateView()
    }

    fun detachView() {
        view = null
        placeHolder = null
    }

    fun attachNavigator(navigator: Navigator) {
        this.navigator = navigator
        updateView()
    }

    fun detachNavigator() {
        navigator = null
    }
}

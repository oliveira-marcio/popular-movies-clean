package com.marcio.popularmoviesclean.state

abstract class Presenter<V, P> {

    protected var view: V? = null
    protected var placeHolder: P? = null

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
}

package com.marcio.popularmoviesclean.state

abstract class Presenter<T> {

    protected var view: T? = null
    protected abstract fun updateView()

    fun attachView(view: T) {
        this.view = view
        updateView()
    }

    fun detachView() {
        view = null
    }
}

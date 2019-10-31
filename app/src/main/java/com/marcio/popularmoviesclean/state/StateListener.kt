package com.marcio.popularmoviesclean.state

interface StateListener<T, E> {
    fun onStateChanged(state: State<T, E>)
}

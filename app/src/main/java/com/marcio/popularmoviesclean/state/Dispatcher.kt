package com.marcio.popularmoviesclean.state

interface Dispatcher {
    fun dispatch(function: () -> Unit, error: (Throwable) -> Unit)
    fun dispatch(function: () -> Unit)
}

package com.marcio.popularmoviesclean.state

interface Dispatcher {
    fun dispatch(execute: () -> Unit, error: (Throwable) -> Unit)
    fun dispatch(execute: () -> Unit)
}

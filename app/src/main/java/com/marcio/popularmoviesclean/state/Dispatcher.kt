package com.marcio.popularmoviesclean.state

interface Dispatcher<T> {
    fun dispatch(function: () -> T, success: (T) -> Unit, error: (Throwable) -> Unit)
}

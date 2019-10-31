package com.marcio.popularmoviesclean.state

interface ErrorFactory<E> {
    fun create(throwable: Throwable): E
}

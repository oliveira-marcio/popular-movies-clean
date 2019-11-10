package com.marcio.popularmoviesclean.state

interface DispatcherFactory {
    fun createSerialDispatcher(name: String): Dispatcher
    fun createMainDispatcher(): Dispatcher
}

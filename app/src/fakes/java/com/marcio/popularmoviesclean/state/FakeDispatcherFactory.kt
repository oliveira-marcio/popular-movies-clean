package com.marcio.popularmoviesclean.state

class FakeDispatcherFactory : DispatcherFactory {
    override fun createSerialDispatcher(name: String) = FakeDispatcher()
    override fun createMainDispatcher() = FakeDispatcher()
}

package com.marcio.popularmoviesclean.state

class FakeDispatcher<T> : Dispatcher<T> {
    override fun dispatch(function: () -> T, success: (T) -> Unit, error: (Throwable) -> Unit) {
        try {
            val result = function.invoke()
            success.invoke(result)
        } catch (e: Throwable) {
            error.invoke(e)
        }
    }
}

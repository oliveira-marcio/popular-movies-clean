package com.marcio.popularmoviesclean.rxjava

import com.marcio.popularmoviesclean.state.Dispatcher
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class RxJavaDispatcher(
    private val scheduler: Scheduler,
    private val disposable: CompositeDisposable = CompositeDisposable()
) : Dispatcher {

    override fun dispatch(execute: () -> Unit, error: (Throwable) -> Unit) {
        disposable.add(
            Completable.fromCallable(execute)
                .subscribeOn(scheduler)
                .subscribe({}, error)
        )
    }

    override fun dispatch(execute: () -> Unit) {
        dispatch(execute, {})
    }
}

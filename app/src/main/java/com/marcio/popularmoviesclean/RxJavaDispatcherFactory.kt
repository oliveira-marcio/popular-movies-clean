package com.marcio.popularmoviesclean

import android.os.HandlerThread
import com.marcio.popularmoviesclean.rxjava.RxJavaDispatcher
import com.marcio.popularmoviesclean.state.Dispatcher
import com.marcio.popularmoviesclean.state.DispatcherFactory
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class RxJavaDispatcherFactory(
    private val publishScheduler: Scheduler
) : DispatcherFactory {
    override fun createSerialDispatcher(name: String): Dispatcher {
        val thread = HandlerThread(name)
        thread.start()
        return RxJavaDispatcher(AndroidSchedulers.from(thread.looper))
    }

    override fun createMainDispatcher(): Dispatcher = RxJavaDispatcher(
        publishScheduler
    )
}

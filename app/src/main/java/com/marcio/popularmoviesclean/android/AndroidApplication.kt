package com.marcio.popularmoviesclean.android

import android.app.Application
import com.marcio.popularmoviesclean.BuildConfig
import com.marcio.popularmoviesclean.DependencyManager
import com.marcio.popularmoviesclean.MoviesApplication
import com.marcio.popularmoviesclean.RxJavaDispatcherFactory
import com.marcio.popularmoviesclean.movies.gateway.HttpMoviesGateway
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayJsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.OkHttpClient
import java.util.Locale

class AndroidApplication : Application() {

    lateinit var dependencyManager: DependencyManager

    override fun onCreate() {
        super.onCreate()
        dependencyManager = MoviesApplication.Builder()
            .registerDispatcherFactory(lazy { RxJavaDispatcherFactory(AndroidSchedulers.mainThread()) })
            .registerMoviesGateway(lazy {
                HttpMoviesGateway(
                    BuildConfig.BASE_URL,
                    BuildConfig.API_KEY,
                    Locale.getDefault().toLanguageTag(),
                    OkHttpClient(),
                    MoviesGatewayJsonParser()
                )
            })
            .start()
    }
}

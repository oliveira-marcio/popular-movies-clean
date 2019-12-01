package com.marcio.popularmoviesclean.android

import android.app.Application
import com.marcio.popularmoviesclean.BuildConfig
import com.marcio.popularmoviesclean.DependencyManager
import com.marcio.popularmoviesclean.MoviesApplication
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.RxJavaDispatcherFactory
import com.marcio.popularmoviesclean.movies.gateway.HttpMoviesGateway
import com.marcio.popularmoviesclean.movies.gateway.MoviesGatewayJsonParser
import com.marcio.popularmoviesclean.picasso.PicassoImageLoader
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.Locale
import okhttp3.OkHttpClient

class AndroidApplication : Application() {

    lateinit var dependencyManager: DependencyManager

    override fun onCreate() {
        super.onCreate()
        dependencyManager = MoviesApplication.Builder()
            .registerDispatcherFactory(lazy { RxJavaDispatcherFactory(AndroidSchedulers.mainThread()) })
            .registerMoviesGateway(lazy {
                HttpMoviesGateway(
                    BuildConfig.BASE_URL,
                    BuildConfig.BASE_POSTER_URL,
                    resources.getInteger(R.integer.thumb_size),
                    BuildConfig.API_KEY,
                    Locale.getDefault().toLanguageTag(),
                    OkHttpClient(),
                    MoviesGatewayJsonParser()
                )
            })
            .registerImageLoader(lazy {
                PicassoImageLoader(
                    Picasso.get(),
                    R.drawable.placeholder
                )
            })
            .start()
    }
}

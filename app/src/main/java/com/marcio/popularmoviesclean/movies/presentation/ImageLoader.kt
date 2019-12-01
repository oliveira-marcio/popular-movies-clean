package com.marcio.popularmoviesclean.movies.presentation

import android.widget.ImageView

interface ImageLoader {
    fun load(
        imageView: ImageView,
        pictureUrl: String,
        reference: String
    )

    fun cancel(reference: String)
}

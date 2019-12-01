package com.marcio.popularmoviesclean.picasso

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.marcio.popularmoviesclean.movies.presentation.ImageLoader
import com.squareup.picasso.Picasso

class PicassoImageLoader(
    private val picasso: Picasso,
    @DrawableRes private val placeholderResource: Int
) : ImageLoader {
    override fun load(
        imageView: ImageView,
        pictureUrl: String,
        reference: String
    ) {
        picasso.load(pictureUrl)
            .placeholder(placeholderResource)
            .tag(reference)
            .into(imageView)
    }

    override fun cancel(reference: String) {
        picasso.cancelTag(reference)
    }
}

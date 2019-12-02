package com.marcio.popularmoviesclean.movies.presentation.main

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.movies.presentation.ImageLoader

class MoviesListViewHolder(
    view: View,
    private val imageLoader: ImageLoader,
    private val imageLoaderReference: String
) : RecyclerView.ViewHolder(view) {
    private val moviePoster = itemView.findViewById<ImageView>(R.id.moviePoster)

    fun setMovie(movieItem: MovieItemViewModel) {
        moviePoster.contentDescription = movieItem.contentDescription
        imageLoader.load(moviePoster, movieItem.imageUrl, imageLoaderReference)
    }
}

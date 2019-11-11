package com.marcio.popularmoviesclean.movies.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marcio.popularmoviesclean.R

class MoviesListViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    private val titleView = itemView.findViewById<TextView>(R.id.movieTitle)

    fun setMovie(movieItem: MovieItemViewModel) {
        titleView.text = movieItem.title
    }
}

package com.marcio.popularmoviesclean.movies.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcio.popularmoviesclean.R
import com.marcio.popularmoviesclean.movies.presentation.ImageLoader

class MoviesListAdapter(
    private val inflater: LayoutInflater,
    private val imageLoader: ImageLoader,
    private val imageLoaderReference: String,
    private val items: MutableList<MovieItemViewModel> = mutableListOf()
) : RecyclerView.Adapter<MoviesListViewHolder>() {

    var clickListener: OnMovieClickListener? = null

    override fun getItemViewType(position: Int) = R.layout.movies_grid_item

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        return MoviesListViewHolder(
            inflater.inflate(viewType, parent, false),
            imageLoader,
            imageLoaderReference,
            clickListener
        )
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.setMovie(items[position])
    }

    fun setMovies(moviesItems: List<MovieItemViewModel>) {
        items.clear()
        items.addAll(moviesItems)
        notifyDataSetChanged()
    }

    interface OnMovieClickListener {
        fun onMovieClick(id: String)
    }
}

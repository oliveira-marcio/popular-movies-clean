package com.marcio.popularmoviesclean.movies.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcio.popularmoviesclean.R

class MoviesListAdapter(
    private val inflater: LayoutInflater,
    private val items: MutableList<MovieItemViewModel> = mutableListOf()
) : RecyclerView.Adapter<MoviesListViewHolder>() {

    override fun getItemViewType(position: Int) = R.layout.movies_list_item

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        return MoviesListViewHolder(
            inflater.inflate(viewType, parent, false)
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
}

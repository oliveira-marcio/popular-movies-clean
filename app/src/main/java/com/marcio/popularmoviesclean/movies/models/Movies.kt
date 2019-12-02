package com.marcio.popularmoviesclean.movies.models

data class Movies(
    val list: List<Movie>,
    val selectedCategory: Category = Category.POPULAR,
    private val selectedMovieId: String = ""
) {
    val selectedMovie: Movie? = list.find { movie ->
        movie.id == selectedMovieId
    }
    val isSelected = selectedMovie != null

    enum class Category {
        POPULAR,
        TOP_RATED
    }
}

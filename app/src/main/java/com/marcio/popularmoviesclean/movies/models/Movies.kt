package com.marcio.popularmoviesclean.movies.models

data class Movies(
    val list: List<Movie>,
    val selectedCategory: Category = Category.POPULAR
) {
    enum class Category {
        POPULAR,
        TOP_RATED
    }
}

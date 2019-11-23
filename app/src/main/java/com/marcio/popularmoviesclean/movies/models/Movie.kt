package com.marcio.popularmoviesclean.movies.models

data class Movie(
    val title: String = "",
    val poster: String = "",
    val synopsis: String = "",
    val averageRating: Double? = null,
    val ratingsCount: Int? = null,
    val releaseDate: String = ""
)

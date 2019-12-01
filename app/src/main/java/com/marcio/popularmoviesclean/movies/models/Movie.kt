package com.marcio.popularmoviesclean.movies.models

data class Movie(
    val id: Int? = null,
    val title: String = "",
    val posterUrl: String = "",
    val synopsis: String = "",
    val averageRating: Double? = null,
    val ratingsCount: Int? = null,
    val releaseDate: String = ""
)

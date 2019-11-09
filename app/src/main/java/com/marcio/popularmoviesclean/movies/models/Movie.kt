package com.marcio.popularmoviesclean.movies.models

data class Movie(
    val title: String,
    val poster: String,
    val synopsis: String,
    val averageRating: Double,
    val ratingsCount: Int,
    val releaseDate: String
)

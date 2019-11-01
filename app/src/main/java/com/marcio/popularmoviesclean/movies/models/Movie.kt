package com.marcio.popularmoviesclean.movies.models

data class Movie(
    private val title: String,
    private val poster: String,
    private val synopsis: String,
    private val averageRating: Double,
    private val ratingsCount: Int,
    private val releaseDate: String
)

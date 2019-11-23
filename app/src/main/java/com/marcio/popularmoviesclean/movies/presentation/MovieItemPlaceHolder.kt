package com.marcio.popularmoviesclean.movies.presentation

import androidx.annotation.DrawableRes

data class MovieItemPlaceHolder(
    val title: String,
    @DrawableRes val imageResource: Int
)

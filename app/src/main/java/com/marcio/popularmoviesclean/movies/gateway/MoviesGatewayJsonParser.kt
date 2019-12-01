package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.movies.models.Movie
import org.json.JSONObject

class MoviesGatewayJsonParser : MoviesGatewayParser {
    override fun parseMovies(
        responseBody: String,
        posterBaseUrl: String,
        posterWidth: Int
    ): List<Movie> {
        val moviesList = mutableListOf<Movie>()

        val response = JSONObject(responseBody)
        val moviesArray = response.getJSONArray("results")

        for (index in 0 until moviesArray.length()) {
            val movieObject = moviesArray.getJSONObject(index)
            val posterUrl =
                if (movieObject.has("poster_path")) {
                    "${posterBaseUrl}w$posterWidth${movieObject.getString("poster_path")}"
                } else {
                    ""
                }

            moviesList.add(
                Movie(
                    movieObject.optString("title"),
                    posterUrl,
                    movieObject.optString("overview"),
                    movieObject.optDouble("vote_average"),
                    movieObject.optInt("vote_count"),
                    movieObject.optString("release_date")
                )
            )
        }
        return moviesList
    }
}

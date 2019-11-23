package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.movies.models.Movie
import org.json.JSONObject

class MoviesGatewayJsonParser : MoviesGatewayParser {
    override fun parseMovies(responseBody: String): List<Movie> {
        val moviesList = mutableListOf<Movie>()

        val response = JSONObject(responseBody)
        val moviesArray = response.getJSONArray("results")

        for (index in 0 until moviesArray.length()) {
            val movieObject = moviesArray.getJSONObject(index)
            moviesList.add(
                Movie(
                    movieObject.optString("title"),
                    movieObject.optString("poster_path"),
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

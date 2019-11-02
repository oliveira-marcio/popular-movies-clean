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
                    movieObject.getString("original_title"),
                    movieObject.getString("poster_path"),
                    movieObject.getString("overview"),
                    movieObject.getDouble("vote_average"),
                    movieObject.getInt("vote_count"),
                    movieObject.getString("release_date")
                )
            )
        }
        return moviesList
    }
}

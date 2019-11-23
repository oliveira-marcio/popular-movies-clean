package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.movies.models.Movie
import com.marcio.popularmoviesclean.movies.models.Movies
import okhttp3.OkHttpClient
import okhttp3.Request

class HttpMoviesGateway(
    private val baseUrl: String,
    private val apiKey: String,
    private val locale: String = "en-US",
    private val httpClient: OkHttpClient,
    private val gatewayParser: MoviesGatewayParser
) : MoviesGateway {

    private val pathMap = mapOf(
        Movies.Category.POPULAR to "popular",
        Movies.Category.TOP_RATED to "top_rated"
    )

    override fun getMovies(
        category: Movies.Category,
        page: Int
    ): List<Movie> {
        val request = Request.Builder()
            .url("${baseUrl}${pathMap.getValue(category)}?api_key=$apiKey&language=$locale&page=$page")
            .get()
            .build()

        val response = httpClient.newCall(request).execute()

        check(response.isSuccessful) { "Failed to get movies from server with status code: ${response.code()}" }
        return gatewayParser.parseMovies(response.body()!!.string())
    }
}

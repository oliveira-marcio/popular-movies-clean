package com.marcio.popularmoviesclean.movies.gateway

import com.marcio.popularmoviesclean.movies.models.Movie
import okhttp3.OkHttpClient
import okhttp3.Request

class HttpMoviesGateway(
    private val baseUrl: String,
    private val apiKey: String,
    private val locale: String = "en-US",
    private val httpClient: OkHttpClient,
    private val gatewayParser: MoviesGatewayParser
) : MoviesGateway {
    override fun getPopularMovies(page: Int) = getMovies("popular", page)

    override fun getTopRatedMovies(page: Int) = getMovies("top_rated", page)

    private fun getMovies(path: String, page: Int): List<Movie> {
        val request = Request.Builder()
            .url("${baseUrl}$path/movies?api_key=$apiKey&language=$locale&page=$page")
            .get()
            .build()

        val response = httpClient.newCall(request).execute()

        check(response.isSuccessful) { "Failed to get movies from server with status code: ${response.code()}" }
        return gatewayParser.parseMovies(response.body()!!.string())
    }
}

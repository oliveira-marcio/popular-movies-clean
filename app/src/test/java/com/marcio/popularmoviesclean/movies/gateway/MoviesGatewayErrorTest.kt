package com.marcio.popularmoviesclean.movies.gateway

import org.json.JSONException
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class MoviesGatewayErrorTest {
    @Test
    fun `Given an IO cause When is network checked Then return true`() {
        val conferenceError = MoviesGatewayError(IOException())

        assertEquals(true, conferenceError.isNetwork)
    }

    @Test
    fun `Given a non-IO cause When is network checked Then return false`() {
        val conferenceError =
            MoviesGatewayError(JSONException("networkError parsing json"))
        assertEquals(false, conferenceError.isNetwork)
    }

    @Test
    fun `Given a null cause When is network checked Then return false`() {
        val conferenceError = MoviesGatewayError(null)

        assertEquals(false, conferenceError.isNetwork)
    }
}

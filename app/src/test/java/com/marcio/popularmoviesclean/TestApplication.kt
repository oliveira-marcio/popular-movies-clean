package com.marcio.popularmoviesclean

import android.app.Application

class TestApplication : Application() {
    init {
        System.setProperty("javax.net.ssl.trustStore", "NONE")
    }
}

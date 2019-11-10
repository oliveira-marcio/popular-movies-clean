package com.marcio.popularmoviesclean

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.marcio.popularmoviesclean.android.ViewContainer

class TestActivity : AppCompatActivity(), ViewContainer {

    var testDependencyManager: DependencyManager? = null

    override val dependencyManager: DependencyManager by lazy {
        testDependencyManager!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LinearLayout(this)
        view.id = 1
        setContentView(view)
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(1, fragment)
            .commit()
    }
}

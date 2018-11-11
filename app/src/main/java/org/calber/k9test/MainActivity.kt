package org.calber.k9test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager?.beginTransaction()
            ?.replace(R.id.root, FilmCardFragment.newInstance(), "detail")
            ?.addToBackStack(null)
            ?.commit()

    }

}

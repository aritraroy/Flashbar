package com.andrognito.flashbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activity = this
        show.setOnClickListener {
            Flashbar.Builder(activity)
                    .position(Flashbar.POSITION_BOTTOM)
                    .show()
        }
    }
}

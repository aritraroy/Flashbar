package com.andrognito.flashbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activity = this
        var flashbar: Flashbar? = null
        show.setOnClickListener {
            if (flashbar == null) {
                flashbar = Flashbar.Builder(activity)
                        .position(FlashbarPosition.BOTTOM )
                        .buttonText("Click")
                        .buttonTextColorRes(R.color.colorPrimary)
                        .iconColorFilterRes(R.color.colorAccent)
                        .showIcon(true)
                        .message("A quick brown fox jumps over the lazy dog!")
                        .build()
            }
            flashbar?.show()
        }

        dismiss.setOnClickListener {
            flashbar?.dismiss()
            flashbar = null
        }
    }
}

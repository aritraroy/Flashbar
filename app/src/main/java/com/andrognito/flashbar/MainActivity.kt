package com.andrognito.flashbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.andrognito.flashbar.listeners.OnActionTapListener
import com.andrognito.flashbar.listeners.OnBarDismissListener
import com.andrognito.flashbar.listeners.OnBarShowListener
import com.andrognito.flashbar.listeners.OnBarTapListener
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
                        .position(Flashbar.FlashbarPosition.TOP)
                        .title("Hello!")
                        .actionText("Click")
                        .actionTapListener(object : OnActionTapListener {
                            override fun onActionTapped(bar: Flashbar) {
                                Log.d("Flashbar", "onActionTapped")
                            }
                        })
                        .barTapListener(object : OnBarTapListener {
                            override fun onBarTapped(flashbar: Flashbar) {
                                Log.d("Flashbar", "onBarTapped")
                            }
                        })
                        .barShownListener(object : OnBarShowListener {
                            override fun onShowing(bar: Flashbar) {
                                Log.d("Flashbar", "onShowing")
                            }

                            override fun onShown(bar: Flashbar) {
                                Log.d("Flashbar", "onShown")
                            }
                        })
                        .duration(1000)
                        .barDismissListener(object : OnBarDismissListener {
                            override fun onDismissing(bar: Flashbar) {
                                Log.d("Flashbar", "onDismissing")
                            }

                            override fun onDismissed(bar: Flashbar, event: Flashbar.FlashbarDismissEvent) {
                                Log.d("Flashbar", "onDismissed: $event")
                            }
                        })
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

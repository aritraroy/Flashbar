package com.andrognito.flashbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
                        .backgroundColorRes(R.color.colorPrimary)
                        .title("Hello!")
                        .enterAnimation(FlashAnim
                                .with(activity)
                                .enter(FlashAnim.Position.TOP)
                                .overshoot()
                                .duration(400)
                                .build())
                        .exitAnimation(FlashAnim
                                .with(activity)
                                .exit(FlashAnim.Position.TOP)
                                .duration(400)
                                .overshoot()
                                .build())
                        .actionText("Close")
                        .modalOverlayColorRes(R.color.modal)
                        .enableSwipeToDismiss()
                        .vibrateOn(Flashbar.Vibration.SHOW, Flashbar.Vibration.DISMISS)
                        .dismissOnTapOutside()
                        .actionTapListener(object : Flashbar.OnActionTapListener {
                            override fun onActionTapped(bar: Flashbar) {
                                Log.d("Flashbar", "onActionTapped")
                                bar.dismiss()
                            }
                        })
                        .barTapListener(object : Flashbar.OnBarTapListener {
                            override fun onBarTapped(flashbar: Flashbar) {
                                Log.d("Flashbar", "onBarTapped")
                            }
                        })
                        .barShownListener(object : Flashbar.OnBarShowListener {
                            override fun onShowing(bar: Flashbar) {
                                Log.d("Flashbar", "onShowing")
                            }

                            override fun onShown(bar: Flashbar) {
                                Log.d("Flashbar", "onShown")
                            }
                        })
                        .barDismissListener(object : Flashbar.OnBarDismissListener {
                            override fun onDismissing(bar: Flashbar, isSwiping: Boolean) {
                                Log.d("Flashbar", "onDismissing: Swipe $isSwiping")
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

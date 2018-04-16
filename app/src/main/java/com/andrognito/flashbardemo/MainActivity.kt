package com.andrognito.flashbardemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.andrognito.flashbar.FlashAnim
import com.andrognito.flashbar.Flashbar
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
                        .backgroundColorRes(R.color.colorPrimaryDark)
                        .title("Hello!")
                        .message("Loading, please wait...")
                        .enterAnimation(FlashAnim
                                .with(activity)
                                .animateBar()
                                .enterFrom(FlashAnim.Position.TOP)
                                .alpha()
                                .overshoot()
                                .duration(450)
                                .build())
                        .exitAnimation(FlashAnim
                                .with(activity)
                                .animateBar()
                                .exitFrom(FlashAnim.Position.TOP)
                                .alpha()
                                .overshoot()
                                .duration(450)
                                .build())
                        .iconAnimation(FlashAnim.with(activity)
                                .animateIcon()
                                .pulse()
                                .duration(400)
                                .build())
                        //.primaryActionText("DOWNLOAD")
                        //.modalOverlayColorRes(R.color.modal)
                        //.showProgress(Flashbar.ProgressPosition.RIGHT)
                        .progressTintRes(R.color.colorAccent)
                        .tapOutsideListener(object : Flashbar.OnTapOutsideListener {
                            override fun onTap(bar: Flashbar) {
                                Log.d("Flashbar", "Tapped outside")
                            }
                        })
                        .showIcon(true)
                        .enableSwipeToDismiss()
                        .positiveActionText("OKAY")
                        .positiveActionTapListener(object : Flashbar.OnActionTapListener {
                            override fun onActionTapped(bar: Flashbar) {
                                Log.d("Flashbar", "Positive action tapped")
                            }
                        })
                        .negativeActionText("CANCEL")
                        .negativeActionTapListener(object : Flashbar.OnActionTapListener {
                            override fun onActionTapped(bar: Flashbar) {
                                Log.d("Flashbar", "Negative action tapped")
                            }
                        })
                        //.vibrateOn(Flashbar.Vibration.DISMISS)
                        //.dismissOnTapOutside()
                        .primaryActionTapListener(object : Flashbar.OnActionTapListener {
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
                        //.showIcon(true)
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

package com.andrognito.flashbardemo

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.andrognito.flashbar.Flashbar
import com.andrognito.flashbar.anim.FlashAnim
import kotlinx.android.synthetic.main.activity_main.*

class KotlinSampleActivity : AppCompatActivity() {

    private val TAG = "Flashbar"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var flashbar: Flashbar? = null

        show.setOnClickListener {
            if (flashbar == null) {
                flashbar = basic()
            }
            flashbar?.show()
        }

        dismiss.setOnClickListener {
            flashbar?.dismiss()
        }
    }

    private fun basic(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .message("This is a basic flashbar")
                .build()
    }

    private fun basicDuration(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .duration(500)
                .message("This is a flashbar with duration")
                .build()
    }

    private fun gravityTop(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .message("Flashbar is shown at the top")
                .build()
    }

    private fun gravityBottom(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .message("Flashbar is shown at the bottom")
                .build()
    }

    private fun titleBasic(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello Flashbar")
                .message("You can put any message of any length here.")
                .build()
    }

    private fun titleAdvanced(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World")
                .titleColorRes(R.color.white)
                .titleSizeInSp(28f)
                .message("The font and size of the text is changed here.")
                .titleTypeface(Typeface.createFromAsset(assets, "BeautifulAndOpenHearted.ttf"))
                .build()
    }

    private fun messageBasic(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World")
                .message(
                        "This is a short message. But your message can be of any length and the "
                                + "with view will dynamically adjust itself. You can try to put "
                                + "very long messages as well. This can be really useful for "
                                + "putting up a lot of information to the user like feature "
                                + "explanation, tutorials, etc.")
                .build()
    }

    private fun messageAdvanced(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World")
                .message("This is a short message")
                .messageColor(ContextCompat.getColor(this, R.color.white))
                .messageSizeInSp(16f)
                .messageTypeface(Typeface.createFromAsset(assets, "BeautifulAndOpenHearted.ttf"))
                .build()
    }

    private fun background(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("The background color can be changed to any color of your choice.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .build()
    }

    private fun backgroundDrawable(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can have gradients by setting background drawable.")
                .backgroundDrawable(R.drawable.bg_gradient)
                .build()
    }

    private fun overlay(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show a modal overlay to give a dim effect in the entire screen.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .showOverlay()
                .build()
    }

    private fun overlayAdvanced(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show a modal overlay to give a dim effect in the entire screen.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .showOverlay()
                .overlayColorRes(R.color.modal)
                .overlayBlockable()
                .build()
    }

    private fun primaryActionBasic(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can click on the primary action button.")
                .primaryActionText("TRY")
                .build()
    }

    private fun primaryActionAdvanced(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .message("You can click on the primary action button.")
                .primaryActionText("TRY")
                .primaryActionTextColorRes(R.color.colorAccent)
                .primaryActionTextSizeInSp(20f)
                .build()
    }

    private fun primaryActionListener(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can click on the primary action button.")
                .primaryActionText("TRY")
                .primaryActionTapListener(object : Flashbar.OnActionTapListener {
                    override fun onActionTapped(bar: Flashbar) {
                        bar.dismiss()
                    }
                })
                .build()
    }

    private fun positiveNegativeAction(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show either or both of the positive/negative buttons and customize them similar to the primary button.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .positiveActionText("YES")
                .negativeActionText("NO")
                .positiveActionTapListener(object : Flashbar.OnActionTapListener {
                    override fun onActionTapped(bar: Flashbar) {
                        bar.dismiss()
                    }
                })
                .negativeActionTapListener(object : Flashbar.OnActionTapListener {
                    override fun onActionTapped(bar: Flashbar) {
                        bar.dismiss()
                    }
                })
                .positiveActionTextColorRes(R.color.colorAccent)
                .negativeActionTextColorRes(R.color.colorAccent)
                .build()
    }

    private fun iconBasic(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show a default icon on the left side of the with view.")
                .showIcon()
                .build()
    }

    private fun iconAdvanced(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show a default icon on the left side of the with view.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .showIcon()
                .icon(R.drawable.ic_drop)
                .iconColorFilterRes(R.color.colorAccent)
                .build()
    }

    private fun progressBasic(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show the progress bar on either the left or right side of the view")
                .showProgress(Flashbar.ProgressPosition.LEFT)
                .build()
    }

    private fun progressAdvanced(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show the progress bar on either the left or right side of the view")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .showProgress(Flashbar.ProgressPosition.RIGHT)
                .progressTintRes(R.color.colorAccent)
                .build()
    }

    private fun enterExitAnimation(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can change the enter/exit animation of the flashbar")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .enterAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(750)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(400)
                        .accelerateDecelerate())
                .build()
    }

    private fun enterExitAnimationSlide(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can change the enter/exit animation of the flashbar")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .enterAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(400)
                        .slideFromLeft()
                        .overshoot())
                .exitAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(250)
                        .slideFromLeft()
                        .accelerate())
                .build()
    }

    private fun iconAnimation(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can show a default icon on the left side of the with view.")
                .backgroundColorRes(R.color.colorPrimaryDark)
                .showIcon()
                .icon(R.drawable.ic_drop)
                .iconColorFilterRes(R.color.colorAccent)
                .iconAnimation(FlashAnim.with(this)
                        .animateIcon()
                        .pulse()
                        .alpha()
                        .duration(750)
                        .accelerate())
                .build()
    }

    private fun showListener(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .message("You can listen to events when the flashbar is shown.")
                .barShowListener(object : Flashbar.OnBarShowListener {
                    override fun onShowing(bar: Flashbar) {
                        Log.d(TAG, "Flashbar is showing")
                    }

                    override fun onShowProgress(bar: Flashbar, progress: Float) {
                        Log.d(TAG, "Flashbar is showing with progress: $progress")
                    }

                    override fun onShown(bar: Flashbar) {
                        Log.d(TAG, "Flashbar is shown")
                    }
                })
                .build()
    }

    private fun dismissListener(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .duration(500)
                .message("You can listen to events when the flashbar is dismissed.")
                .barDismissListener(object : Flashbar.OnBarDismissListener {
                    override fun onDismissing(bar: Flashbar, isSwiped: Boolean) {
                        Log.d(TAG, "Flashbar is dismissing with $isSwiped")
                    }

                    override fun onDismissProgress(bar: Flashbar, progress: Float) {
                        Log.d(TAG, "Flashbar is dismissing with progress $progress")
                    }

                    override fun onDismissed(bar: Flashbar,
                                             event: Flashbar.DismissEvent) {
                        Log.d(TAG, "Flashbar is dismissed with event $event")
                    }
                })
                .build()
    }

    private fun barTap(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can listen to tap events inside or outside te bar.")
                .listenBarTaps(object : Flashbar.OnTapListener {
                    override fun onTap(flashbar: Flashbar) {
                        Log.d(TAG, "Bar tapped")
                    }
                })
                .listenOutsideTaps(object : Flashbar.OnTapListener {
                    override fun onTap(flashbar: Flashbar) {
                        Log.d(TAG, "Outside tapped")
                    }
                })
                .build()
    }

    private fun swipeToDismiss(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .title("Hello World!")
                .message("You can swipe the flashbar to dismiss it.")
                .enableSwipeToDismiss()
                .build()
    }

    private fun barShadow(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .message("You can swipe the flashbar to dismiss it.")
                .castShadow(true, 4)
                .build()
    }

    private fun vibration(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World!")
                .message("You can swipe the flashbar to dismiss it.")
                .vibrateOn(Flashbar.Vibration.SHOW, Flashbar.Vibration.DISMISS)
                .build()
    }
}
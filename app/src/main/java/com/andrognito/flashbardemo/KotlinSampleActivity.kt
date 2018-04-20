package com.andrognito.flashbardemo

import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.andrognito.flashbar.Flashbar
import kotlinx.android.synthetic.main.activity_main.*

class KotlinSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var flashbar: Flashbar? = null

        show.setOnClickListener {
            if (flashbar == null) {
                flashbar = primaryActionListener()
            }
            flashbar?.show()
        }

        dismiss.setOnClickListener {
            flashbar?.dismiss()
        }
    }

    private fun basic(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
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
                .title("Hello World!")
                .build()
    }

    private fun titleAdvanced(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Hello World")
                .titleColorRes(R.color.white)
                .titleSizeInSp(24f)
                .titleTypeface(Typeface.createFromAsset(assets, "BeautifulAndOpenHearted.ttf"))
                .build()
    }

    private fun messageBasic(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
                .message(
                        "This is a short message. But your message can be of any length and the " + "withView will dynamically adjust itself.")
                .build()
    }

    private fun messageAdvanced(): Flashbar {
        return Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.TOP)
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
                .progressTintRes(R.color.colorAccent, PorterDuff.Mode.SRC_ATOP)
                .build()
    }
}
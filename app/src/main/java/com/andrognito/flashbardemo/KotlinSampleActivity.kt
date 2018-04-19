package com.andrognito.flashbardemo

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
                .gravity(Flashbar.Gravity.TOP)
                .message("This is a basic flashbar")
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
                        "This is a short message. But your message can be of any length and the " + "view will dynamically adjust itself.")
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
}
package com.andrognito.flashbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout


class FlashbarView : RelativeLayout {

    private lateinit var flashbarRootView: LinearLayout

    constructor(context: Context) : super(context, null, 0) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.flash_bar_view, this)

        flashbarRootView = findViewById(R.id.flash_bar_root)
    }

    internal fun setBarBackground(drawable: Drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.flashbarRootView.background = drawable
        } else {
            this.flashbarRootView.setBackgroundDrawable(drawable)
        }
    }

    internal fun setBarBackgroundColor(@ColorInt color: Int) {
        this.flashbarRootView.setBackgroundColor(color)
    }
}

/**
 * Container view matching the height and width of the parent to hold a FlashBarView
 */
class FlashbarContainerView(context: Context) : RelativeLayout(context) {

    private lateinit var flashbarView: FlashbarView

    fun add(flashbarView: FlashbarView) {
        this.flashbarView = flashbarView
        addView(flashbarView)
    }
}

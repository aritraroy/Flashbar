package com.andrognito.flashbar

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class FlashbarView : RelativeLayout {

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
    }
}

/**
 * Container view matching the height and width of the parent to hold a FlashBarView
 */
class FlashbarContainerView(context: Context) : RelativeLayout(context)

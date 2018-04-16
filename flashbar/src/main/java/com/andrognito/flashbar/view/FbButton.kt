package com.andrognito.flashbar.view

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.andrognito.flashbar.R

internal class FbButton : TextView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.style.FbButtonStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}
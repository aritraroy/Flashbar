package com.andrognito.flashbar.view

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.JELLY_BEAN
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.andrognito.flashbar.R
import com.andrognito.flashbar.view.ShadowView.ShadowType.BOTTOM
import com.andrognito.flashbar.view.ShadowView.ShadowType.TOP

internal class ShadowView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    internal fun applyShadow(type: ShadowType) {
        when (type) {
            TOP -> setShadow(R.drawable.shadow_top)
            BOTTOM -> setShadow(R.drawable.shadow_bottom)
        }
    }

    private fun setShadow(@DrawableRes id: Int) {
        val shadow = ContextCompat.getDrawable(context, id)
        if (SDK_INT >= JELLY_BEAN) {
            this.background = shadow
        } else {
            this.setBackgroundDrawable(shadow)
        }
    }

    enum class ShadowType {
        TOP, BOTTOM
    }
}
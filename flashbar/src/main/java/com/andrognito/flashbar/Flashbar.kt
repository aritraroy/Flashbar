package com.andrognito.flashbar

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.andrognito.flashbar.Flashbar.FlashbarPosition.BOTTOM
import com.andrognito.flashbar.Flashbar.FlashbarPosition.TOP
import com.andrognito.flashbar.utils.*
import com.andrognito.flashbar.utils.NavigationBarPosition.LEFT
import com.andrognito.flashbar.utils.NavigationBarPosition.RIGHT

class Flashbar {

    private var builder: Builder

    private var flashbarContainerView: FlashbarContainerView? = null

    private constructor(builder: Builder) {
        this.builder = builder
        initialize()
    }

    fun show() {
        val rootView = getActivityRootView(builder.activity)
        rootView?.addView(flashbarContainerView, ViewGroup.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT))
    }

    fun dismiss() {
        (flashbarContainerView?.parent as? ViewGroup)?.removeView(flashbarContainerView)
    }

    private lateinit var flashbarView: FlashbarView

    private fun initialize() {
        flashbarContainerView = FlashbarContainerView(builder.activity)
        flashbarView = FlashbarView(builder.activity)

        val flashbarViewLayoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        when (builder.position) {
            TOP -> {
                val flashbarViewContent = flashbarView.findViewById<View>(R.id.flash_bar_content)
                val flashbarViewContentLayoutParams =
                        flashbarViewContent.layoutParams as LinearLayout.LayoutParams
                flashbarViewContentLayoutParams.topMargin = getStatusBarHeightInPixels(builder.activity)
                flashbarViewContent.layoutParams = flashbarViewContentLayoutParams
                flashbarViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
            }
            BOTTOM -> {
                flashbarViewLayoutParams.bottomMargin = getNavigationBarHeightInPixels(builder.activity)
                flashbarViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            }
        }

        val navigationBarPosition = getNavigationBarPosition(builder.activity)
        val navigationBarWidth = getNavigationBarWidthInPixels(builder.activity)

        when (navigationBarPosition) {
            LEFT -> flashbarViewLayoutParams.leftMargin = navigationBarWidth
            RIGHT -> flashbarViewLayoutParams.rightMargin = navigationBarWidth
        }

        flashbarView.layoutParams = flashbarViewLayoutParams
        flashbarContainerView?.add(flashbarView)
    }

    class Builder {

        internal var activity: Activity
        internal var position: FlashbarPosition = TOP

        private lateinit var title: String

        constructor(activity: Activity) {
            this.activity = activity
        }

        fun title(title: String) = apply { this.title = title }

        fun position(position: FlashbarPosition) = apply { this.position = position }

        fun build() = Flashbar(this)

        fun show() = build().show()
    }

    enum class FlashbarPosition {
        TOP,
        BOTTOM
    }
}

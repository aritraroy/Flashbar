package com.andrognito.flashbar

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.andrognito.flashbar.Flashbar.FlashbarPosition.TOP
import com.andrognito.flashbar.utils.getActivityRootView

class Flashbar {

    private var builder: Builder

    private lateinit var flashbarContainerView: FlashbarContainerView
    private lateinit var flashbarView: FlashbarView

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
        (flashbarContainerView.parent as? ViewGroup)?.removeView(flashbarContainerView)
    }

    private fun initialize() {
        flashbarContainerView = FlashbarContainerView(builder.activity)
        flashbarView = FlashbarView(builder.activity)
        flashbarView.adjustWitPositionAndOrientation(builder.activity, builder.position)

        flashbarContainerView.add(flashbarView)
    }

    class Builder {

        internal var activity: Activity
        internal var position: FlashbarPosition = TOP

        private lateinit var backgroundDrawable: Drawable
        private lateinit var title: String

        constructor(activity: Activity) {
            this.activity = activity
        }

        fun title(title: String) = apply { this.title = title }

        fun position(position: FlashbarPosition) = apply { this.position = position }

        fun background(drawable: Drawable) = apply { this.backgroundDrawable = drawable }

        fun build() = Flashbar(this)

        fun show() = build().show()
    }

    enum class FlashbarPosition {
        TOP,
        BOTTOM
    }
}

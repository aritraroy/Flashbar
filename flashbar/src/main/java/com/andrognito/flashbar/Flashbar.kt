package com.andrognito.flashbar

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RelativeLayout
import android.widget.RelativeLayout.LayoutParams.MATCH_PARENT
import com.andrognito.flashbar.utils.getNavigationBarHeightInPixels
import com.andrognito.flashbar.utils.getStatusBarHeightInPixels

class Flashbar {

    private var builder: Builder

    private constructor(builder: Builder) {
        this.builder = builder
        initialize(builder)
    }

    fun dismiss() {
    }

    private fun initialize(builder: Builder) {

    }

    private fun show() {
        val flashBarContainerView = FlashbarContainerView(builder.activity)
        val flashBarView = FlashbarView(builder.activity)

        val flashBarContainerLayoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        val flashBarViewLayoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)

        if (builder.position == POSITION_TOP) {
            val flashBarViewContent = flashBarView.findViewById<View>(R.id.flash_bar_content)
            flashBarViewContent.setPadding(0, getStatusBarHeightInPixels(builder.activity), 0, 0)
            flashBarViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        } else {
            flashBarContainerLayoutParams.bottomMargin = getNavigationBarHeightInPixels(builder.activity)
            flashBarViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        }

        flashBarView.layoutParams = flashBarViewLayoutParams
        flashBarContainerView.addView(flashBarView)

        val rootView = getActivityRootView(builder.activity)
        rootView?.addView(flashBarContainerView, flashBarContainerLayoutParams)
    }

    private fun getActivityRootView(activity: Activity?): ViewGroup? {
        if (activity == null || activity.window == null || activity.window.decorView == null) {
            return null
        }

        return activity.window.decorView as ViewGroup
    }

    class Builder {

        internal var activity: Activity

        internal lateinit var title: String

        internal var position: Int = POSITION_TOP

        constructor(activity: Activity) {
            this.activity = activity
        }

        fun title(title: String) = apply { this.title = title }

        fun position(position: Int) = apply { this.position = position }

        fun build() = Flashbar(this)

        fun show() = build().show()
    }

    companion object {
        const val POSITION_TOP = 0
        const val POSITION_BOTTOM = 1
    }
}

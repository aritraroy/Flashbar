package com.andrognito.flashbar

import android.app.Activity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RelativeLayout
import android.widget.RelativeLayout.ALIGN_PARENT_TOP
import android.widget.RelativeLayout.LayoutParams.MATCH_PARENT


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
        val flashBarView  = FlashbarView(builder.activity)

        val flashBarContainerLayoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        val flashBarViewLayoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        flashBarViewLayoutParams.addRule(ALIGN_PARENT_TOP)

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

        private lateinit var title: String

        constructor(activity: Activity) {
            this.activity = activity
        }

        fun title(title: String) = apply { this.title = title }

        fun build() = Flashbar(this)

        fun show() = build().show()
    }
}

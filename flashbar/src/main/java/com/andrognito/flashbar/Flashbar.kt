package com.andrognito.flashbar

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout.LayoutParams.MATCH_PARENT
import com.andrognito.flashbar.Flashbar.FlashbarPosition.TOP
import com.andrognito.flashbar.utils.getActivityRootView


class Flashbar {

    private var builder: Builder

    private lateinit var flashbarContainerView: FlashbarContainerView
    private lateinit var flashbarView: FlashbarView

    private var isShowing = false
    private var isShown = false
    private var isDismissing = false

    private val enterAnimationListener = object : Animation.AnimationListener {

        override fun onAnimationStart(animation: Animation) {
            isShowing = true
            flashbarContainerView.visibility = VISIBLE
        }

        override fun onAnimationEnd(animation: Animation) {
            isShowing = false
            isShown = true
        }

        override fun onAnimationRepeat(animation: Animation) {
            // NO-OP
        }
    }

    private val exitAnimationListener = object : Animation.AnimationListener {

        override fun onAnimationStart(animation: Animation?) {
            isDismissing = true
        }

        override fun onAnimationEnd(animation: Animation?) {
            isDismissing = false
            isShown = false
            (flashbarContainerView.parent as? ViewGroup)?.removeView(flashbarContainerView)
        }

        override fun onAnimationRepeat(animation: Animation?) {
            // NO-OP
        }
    }

    private constructor(builder: Builder) {
        this.builder = builder
        initialize()
    }

    fun show() {
        if (isShowing || isShown) {
            return
        }

        val activityRootView = getActivityRootView(builder.activity)
        activityRootView?.addView(flashbarContainerView, LayoutParams(MATCH_PARENT, MATCH_PARENT))

        builder.enterAnimation.setAnimationListener(enterAnimationListener)
        flashbarContainerView.startAnimation(builder.enterAnimation)
    }

    fun dismiss() {
        if (isDismissing) {
            return
        }

        builder.exitAnimation.setAnimationListener(exitAnimationListener)
        flashbarContainerView.startAnimation(builder.exitAnimation)
    }

    fun isShowing() = isShowing

    fun isShown() = isShown

    private fun initialize() {
        flashbarContainerView = FlashbarContainerView(builder.activity)
        flashbarView = FlashbarView(builder.activity)

        flashbarView.adjustWitPositionAndOrientation(builder.activity, builder.position)
        flashbarContainerView.add(flashbarView)
    }

    class Builder {
        internal var activity: Activity

        internal lateinit var title: String
        internal lateinit var position: FlashbarPosition
        internal lateinit var backgroundDrawable: Drawable
        internal lateinit var enterAnimation: Animation
        internal lateinit var exitAnimation: Animation

        constructor(activity: Activity) {
            this.activity = activity

            position = TOP
            enterAnimation = AnimationUtils.loadAnimation(activity, R.anim.slide_in_from_top)
            exitAnimation = AnimationUtils.loadAnimation(activity, R.anim.slide_out_from_top)
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

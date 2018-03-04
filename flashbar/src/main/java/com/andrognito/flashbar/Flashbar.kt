package com.andrognito.flashbar

import android.app.Activity
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.andrognito.flashbar.Flashbar.FlashbarPosition.BOTTOM
import com.andrognito.flashbar.Flashbar.FlashbarPosition.TOP


class Flashbar {

    private var builder: Builder

    private lateinit var flashbarContainerView: FlashbarContainerView
    private lateinit var flashbarView: FlashbarView

    private constructor(builder: Builder) {
        this.builder = builder
        construct()
    }

    fun show() {
        flashbarContainerView.show(builder.activity)
    }

    fun dismiss() {
        flashbarContainerView.dismiss()
    }

    private fun construct() {
        flashbarContainerView = FlashbarContainerView(builder.activity)
        flashbarView = FlashbarView(builder.activity)

        flashbarContainerView.adjustPositionAndOrientation(builder.activity)
        flashbarView.adjustWitPositionAndOrientation(builder.activity, builder.position)
        flashbarContainerView.add(flashbarView)

        initializeContainer()
    }

    private fun initializeContainer() {
        with(flashbarContainerView) {
            setTitle(builder.title)
            setMessage(builder.message)
            setBarBackgroundColor(builder.backgroundColor)
            setBarBackgroundDrawable(builder.backgroundDrawable)
            setEnterAnimation(builder.enterAnimation!!)
            setExitAnimation(builder.exitAnimation!!)
        }
    }

    fun isShowing() = flashbarContainerView.isBarShowing()

    fun isShown() = flashbarContainerView.isBarShown()

    class Builder {
        internal var activity: Activity

        internal var title: String? = null
        internal var message: String? = null
        internal var position: FlashbarPosition = TOP
        internal var backgroundColor: Int? = null
        internal var backgroundDrawable: Drawable? = null

        internal var enterAnimation: Animation? = null
        internal var exitAnimation: Animation? = null

        constructor(activity: Activity) {
            this.activity = activity
        }

        fun title(title: String) = apply { this.title = title }

        fun title(@StringRes titleId: Int) = apply { this.title = activity.getString(titleId) }

        fun message(message: String) = apply { this.message = message }

        fun message(@StringRes messageId: Int) = apply {
            this.message = activity.getString(messageId)
        }

        fun position(position: FlashbarPosition) = apply { this.position = position }

        fun backgroundDrawable(drawable: Drawable) = apply { this.backgroundDrawable = drawable }

        fun backgroundDrawable(@DrawableRes drawableId: Int) = apply {
            this.backgroundDrawable = ContextCompat.getDrawable(activity, drawableId)
        }

        fun backgroundColor(@ColorInt color: Int) = apply { this.backgroundColor = color }

        fun backgroundColorRes(@ColorRes colorId: Int) = apply {
            this.backgroundColor = ContextCompat.getColor(activity, colorId)
        }

        fun enterAnimation(animation: Animation) = apply { this.enterAnimation = animation }

        fun enterAnimation(animationId: Int) = apply {
            this.enterAnimation = AnimationUtils.loadAnimation(activity, animationId)
        }

        fun exitAnimation(animation: Animation) = apply { this.exitAnimation = animation }

        fun exitAnimation(animationId: Int) = apply {
            this.exitAnimation = AnimationUtils.loadAnimation(activity, animationId)
        }

        fun build(): Flashbar {
            initializeAnimation()
            return Flashbar(this)
        }

        fun show() = build().show()

        private fun initializeAnimation() {
            if (enterAnimation == null) {
                enterAnimation = when (position) {
                    BOTTOM -> AnimationUtils.loadAnimation(activity, R.anim.enter_from_bottom)
                    TOP -> AnimationUtils.loadAnimation(activity, R.anim.enter_from_top)
                }
            }

            if (exitAnimation == null) {
                exitAnimation = when (position) {
                    BOTTOM -> AnimationUtils.loadAnimation(activity, R.anim.exit_from_bottom)
                    TOP -> AnimationUtils.loadAnimation(activity, R.anim.exit_from_top)
                }
            }
        }
    }

    enum class FlashbarPosition {
        TOP,
        BOTTOM
    }
}

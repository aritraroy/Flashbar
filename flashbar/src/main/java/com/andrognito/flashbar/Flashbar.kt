package com.andrognito.flashbar

import android.app.Activity
import android.graphics.drawable.Drawable
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
        initialize()
    }

    fun show() {
        flashbarContainerView.show(builder.activity)
    }

    fun dismiss() {
        flashbarContainerView.dismiss()
    }

    private fun initialize() {
        flashbarContainerView = FlashbarContainerView(builder.activity)
        flashbarView = FlashbarView(builder.activity)

        flashbarContainerView.adjustPositionAndOrientation(builder.activity)
        flashbarView.adjustWitPositionAndOrientation(builder.activity, builder.position)

        flashbarContainerView.setEnterAnimation(builder.enterAnimation)
        flashbarContainerView.setExitAnimation(builder.exitAnimation)

        flashbarContainerView.add(flashbarView)
    }

    fun isShowing() = flashbarContainerView.isBarShowing()

    fun isShown() = flashbarContainerView.isBarShown()

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
            enterAnimation = AnimationUtils.loadAnimation(activity, R.anim.enter_from_top)
            exitAnimation = AnimationUtils.loadAnimation(activity, R.anim.exit_from_top)
        }

        fun title(title: String) = apply { this.title = title }

        fun position(position: FlashbarPosition) = apply { this.position = position }

        fun background(drawable: Drawable) = apply { this.backgroundDrawable = drawable }

        fun build(): Flashbar {
            if (position == BOTTOM) {
                enterAnimation = AnimationUtils.loadAnimation(activity, R.anim.enter_from_bottom)
                exitAnimation = AnimationUtils.loadAnimation(activity, R.anim.exit_from_bottom)
            }
            return Flashbar(this)
        }

        fun show() = build().show()
    }

    enum class FlashbarPosition {
        TOP,
        BOTTOM
    }
}

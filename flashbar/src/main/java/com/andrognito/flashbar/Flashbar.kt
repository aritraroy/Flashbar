package com.andrognito.flashbar

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.andrognito.flashbar.FlashbarPosition.BOTTOM
import com.andrognito.flashbar.FlashbarPosition.TOP


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

        decorateContainer()
    }

    private fun decorateContainer() {
        with(flashbarContainerView) {
            setTitle(builder.title)
            setTitleTypeface(builder.titleTypeface)
            setTitleSizeInPx(builder.titleSizeInPx)
            setTitleSizeInSp(builder.titleSizeInSp)
            setTitleColor(builder.titleColor)

            setMessage(builder.message)
            setMessageTypeface(builder.messageTypeface)
            setMessageSizeInPx(builder.messageSizeInPx)
            setMessageSizeInSp(builder.messageSizeInSp)
            setMessageColor(builder.messageColor)

            showIcon(builder.showIcon)
            setIconDrawable(builder.iconDrawable)
            setIconBitmap(builder.iconBitmap)
            setIconColorFilter(builder.iconColorFilter, builder.iconColorFilterMode)

            setBarBackgroundColor(builder.backgroundColor)
            setBarBackgroundDrawable(builder.backgroundDrawable)

            setEnterAnimation(builder.enterAnimation!!)
            setExitAnimation(builder.exitAnimation!!)
        }
    }

    fun isShowing() = flashbarContainerView.isBarShowing()

    fun isShown() = flashbarContainerView.isBarShown()

    class Builder(internal var activity: Activity) {

        internal var position: FlashbarPosition = TOP

        internal var title: String? = null
        internal var titleTypeface: Typeface? = null
        internal var titleSizeInPx: Float? = null
        internal var titleSizeInSp: Float? = null
        internal var titleColor: Int? = null

        internal var message: String? = null
        internal var messageTypeface: Typeface? = null
        internal var messageSizeInPx: Float? = null
        internal var messageSizeInSp: Float? = null
        internal var messageColor: Int? = null

        internal var showIcon: Boolean = false
        internal var iconDrawable: Drawable? = null
        internal var iconBitmap: Bitmap? = null
        internal var iconColorFilter: Int? = null
        internal var iconColorFilterMode: PorterDuff.Mode? = null

        internal var backgroundColor: Int? = null
        internal var backgroundDrawable: Drawable? = null

        internal var enterAnimation: Animation? = null
        internal var exitAnimation: Animation? = null

        fun title(title: String) = apply { this.title = title }

        fun title(@StringRes titleId: Int) = apply { this.title = activity.getString(titleId) }

        fun titleTypeface(typeface: Typeface) = apply { this.titleTypeface = typeface }

        fun titleSizeInPx(size: Float) = apply { this.titleSizeInPx = size }

        fun titleSizeInSp(size: Float) = apply { this.titleSizeInSp = size }

        fun titleColor(color: Int) = apply { this.titleColor = color }

        fun titleColorRes(colorId: Int) = apply {
            this.titleColor = ContextCompat.getColor(activity, colorId)
        }

        fun message(message: String) = apply { this.message = message }

        fun message(@StringRes messageId: Int) = apply {
            this.message = activity.getString(messageId)
        }

        fun messageTypeface(typeface: Typeface) = apply { this.messageTypeface = typeface }

        fun messageSizeInPx(size: Float) = apply { this.messageSizeInPx = size }

        fun messageSizeInSp(size: Float) = apply { this.messageSizeInSp = size }

        fun messageColor(color: Int) = apply { this.messageColor = color }

        fun messageColorRes(colorId: Int) = apply {
            this.messageColor = ContextCompat.getColor(activity, colorId)
        }

        fun showIcon(showIcon: Boolean) = apply { this.showIcon = showIcon }

        fun icon(icon: Drawable) = apply { this.iconDrawable = icon }

        fun icon(@DrawableRes iconId: Int) = apply {
            this.iconDrawable = ContextCompat.getDrawable(activity, iconId)
        }

        fun icon(bitmap: Bitmap) = apply { this.iconBitmap = bitmap }

        fun iconColorFilter(@ColorInt color: Int, mode: PorterDuff.Mode? = null) = apply {
            this.iconColorFilter = color
            this.iconColorFilterMode = mode
        }

        fun iconColorFilterRes(@ColorRes colorId: Int, mode: PorterDuff.Mode? = null) = apply {
            this.iconColorFilter = ContextCompat.getColor(activity, colorId)
            this.iconColorFilterMode = mode
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

        fun enterAnimation(@AnimRes animationId: Int) = apply {
            this.enterAnimation = AnimationUtils.loadAnimation(activity, animationId)
        }

        fun exitAnimation(animation: Animation) = apply { this.exitAnimation = animation }

        fun exitAnimation(@AnimRes animationId: Int) = apply {
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
                    TOP -> AnimationUtils.loadAnimation(activity, R.anim.enter_from_top)
                    BOTTOM -> AnimationUtils.loadAnimation(activity, R.anim.enter_from_bottom)
                }
            }

            if (exitAnimation == null) {
                exitAnimation = when (position) {
                    TOP -> AnimationUtils.loadAnimation(activity, R.anim.exit_from_top)
                    BOTTOM -> AnimationUtils.loadAnimation(activity, R.anim.exit_from_bottom)
                }
            }
        }
    }
}

enum class FlashbarPosition {
    TOP,
    BOTTOM
}
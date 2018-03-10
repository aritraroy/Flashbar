package com.andrognito.flashbar

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.text.Spanned
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.andrognito.flashbar.FlashbarPosition.BOTTOM
import com.andrognito.flashbar.FlashbarPosition.TOP
import com.andrognito.flashbar.listeners.OnActionTapListener
import com.andrognito.flashbar.listeners.OnBarTapListener

class Flashbar {

    private var builder: Builder
    private lateinit var flashbarContainerView: FlashbarContainerView

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
        flashbarContainerView.addParent(this)

        flashbarContainerView.adjustPositionAndOrientation(builder.activity)
        flashbarContainerView.construct(builder.activity, builder.position)

        decorateContainer()
    }

    private fun decorateContainer() {
        with(flashbarContainerView) {
            setBarBackgroundColor(builder.backgroundColor)
            setBarBackgroundDrawable(builder.backgroundDrawable)
            setBarTapListener(builder.onBarTapListener)

            setTitle(builder.title)
            setTitleSpanned(builder.titleSpanned)
            setTitleTypeface(builder.titleTypeface)
            setTitleSizeInPx(builder.titleSizeInPx)
            setTitleSizeInSp(builder.titleSizeInSp)
            setTitleColor(builder.titleColor)
            setTitleAppearance(builder.titleAppearance)

            setMessage(builder.message)
            setMessageSpanned(builder.messageSpanned)
            setMessageTypeface(builder.messageTypeface)
            setMessageSizeInPx(builder.messageSizeInPx)
            setMessageSizeInSp(builder.messageSizeInSp)
            setMessageColor(builder.messageColor)
            setMessageAppearance(builder.messageAppearance)

            setActionText(builder.actionText)
            setActionTextSpanned(builder.actionTextSpanned)
            setActionTextTypeface(builder.actionTextTypeface)
            setActionTextSizeInPx(builder.actionTextSizeInPx)
            setActionTextSizeInSp(builder.actionTextSizeInSp)
            setActionTextColor(builder.actionTextColor)
            setActionTextAppearance(builder.actionTextAppearance)
            setActionOnTapListener(builder.onActionTapListener)

            showIcon(builder.showIcon)
            setIconDrawable(builder.iconDrawable)
            setIconBitmap(builder.iconBitmap)
            setIconColorFilter(builder.iconColorFilter, builder.iconColorFilterMode)

            setEnterAnimation(builder.enterAnimation!!)
            setExitAnimation(builder.exitAnimation!!)
        }
    }

    fun isShowing() = flashbarContainerView.isBarShowing()

    fun isShown() = flashbarContainerView.isBarShown()

    class Builder(internal var activity: Activity) {

        internal var position: FlashbarPosition = TOP
        internal var backgroundColor: Int? = null
        internal var backgroundDrawable: Drawable? = null
        internal var onBarTapListener: OnBarTapListener? = null

        internal var title: String? = null
        internal var titleSpanned: Spanned? = null
        internal var titleTypeface: Typeface? = null
        internal var titleSizeInPx: Float? = null
        internal var titleSizeInSp: Float? = null
        internal var titleColor: Int? = null
        internal var titleAppearance: Int? = null

        internal var message: String? = null
        internal var messageSpanned: Spanned? = null
        internal var messageTypeface: Typeface? = null
        internal var messageSizeInPx: Float? = null
        internal var messageSizeInSp: Float? = null
        internal var messageColor: Int? = null
        internal var messageAppearance: Int? = null

        internal var actionText: String? = null
        internal var actionTextSpanned: Spanned? = null
        internal var actionTextTypeface: Typeface? = null
        internal var actionTextSizeInPx: Float? = null
        internal var actionTextSizeInSp: Float? = null
        internal var actionTextColor: Int? = null
        internal var actionTextAppearance: Int? = null
        internal var onActionTapListener: OnActionTapListener? = null

        internal var showIcon: Boolean = false
        internal var iconDrawable: Drawable? = null
        internal var iconBitmap: Bitmap? = null
        internal var iconColorFilter: Int? = null
        internal var iconColorFilterMode: PorterDuff.Mode? = null

        internal var enterAnimation: Animation? = null
        internal var exitAnimation: Animation? = null

        fun position(position: FlashbarPosition) = apply { this.position = position }

        fun backgroundDrawable(drawable: Drawable) = apply { this.backgroundDrawable = drawable }

        fun backgroundDrawable(@DrawableRes drawableId: Int) = apply {
            this.backgroundDrawable = ContextCompat.getDrawable(activity, drawableId)
        }

        fun backgroundColor(@ColorInt color: Int) = apply { this.backgroundColor = color }

        fun barTapListener(barTapListener: OnBarTapListener) = apply { this.onBarTapListener = barTapListener }

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

        fun title(title: String) = apply { this.title = title }

        fun title(@StringRes titleId: Int) = apply { this.title = activity.getString(titleId) }

        fun title(title: Spanned) = apply { this.titleSpanned = title }

        fun titleTypeface(typeface: Typeface) = apply { this.titleTypeface = typeface }

        fun titleSizeInPx(size: Float) = apply { this.titleSizeInPx = size }

        fun titleSizeInSp(size: Float) = apply { this.titleSizeInSp = size }

        fun titleColor(color: Int) = apply { this.titleColor = color }

        fun titleColorRes(colorId: Int) = apply {
            this.titleColor = ContextCompat.getColor(activity, colorId)
        }

        fun titleAppearance(@StyleRes appearance: Int) = apply {
            this.titleAppearance = appearance
        }

        fun message(message: String) = apply { this.message = message }

        fun message(@StringRes messageId: Int) = apply {
            this.message = activity.getString(messageId)
        }

        fun message(message: Spanned) = apply { this.messageSpanned = message }

        fun messageTypeface(typeface: Typeface) = apply { this.messageTypeface = typeface }

        fun messageSizeInPx(size: Float) = apply { this.messageSizeInPx = size }

        fun messageSizeInSp(size: Float) = apply { this.messageSizeInSp = size }

        fun messageColor(color: Int) = apply { this.messageColor = color }

        fun messageColorRes(colorId: Int) = apply {
            this.messageColor = ContextCompat.getColor(activity, colorId)
        }

        fun messageAppearance(@StyleRes appearance: Int) = apply {
            this.titleAppearance = appearance
        }

        fun actionText(text: String) = apply { this.actionText = text }

        fun actionText(@StringRes actionTextId: Int) = apply {
            this.actionText = activity.getString(actionTextId)
        }

        fun actionText(actionText: Spanned) = apply { this.actionTextSpanned = actionText }

        fun actionTextTypeface(typeface: Typeface) = apply { this.actionTextTypeface = typeface }

        fun actionTextSizeInPx(size: Float) = apply { this.actionTextSizeInPx = size }

        fun actionTextSizeInSp(size: Float) = apply { this.actionTextSizeInSp = size }

        fun actionTextColor(color: Int) = apply { this.actionTextColor = color }

        fun actionTextColorRes(colorId: Int) = apply {
            this.actionTextColor = ContextCompat.getColor(activity, colorId)
        }

        fun actionTextAppearance(@StyleRes appearance: Int) = apply {
            this.actionTextAppearance = appearance
        }

        fun actionTapListener(onActionTapListener: OnActionTapListener) = apply {
            this.onActionTapListener = onActionTapListener
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
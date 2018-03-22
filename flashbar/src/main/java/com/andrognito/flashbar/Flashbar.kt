package com.andrognito.flashbar

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.text.Spanned
import com.andrognito.flashbar.Flashbar.FlashbarPosition.BOTTOM
import com.andrognito.flashbar.Flashbar.FlashbarPosition.TOP

class Flashbar private constructor(private var builder: Builder) {

    private lateinit var flashbarContainerView: FlashbarContainerView
    private lateinit var flashbarView: FlashbarView

    fun show() {
        flashbarContainerView.show(builder.activity)
    }

    fun dismiss() {
        flashbarContainerView.dismiss()
    }

    fun isShowing() = flashbarContainerView.isBarShowing()

    fun isShown() = flashbarContainerView.isBarShown()

    private fun construct() {
        flashbarContainerView = FlashbarContainerView(builder.activity)
        flashbarContainerView.adjustPositionAndOrientation(builder.activity)
        flashbarContainerView.addParent(this)

        flashbarView = FlashbarView(builder.activity)
        flashbarView.init(builder.position, builder.castShadow, builder.shadowStrength)
        flashbarView.adjustWitPositionAndOrientation(builder.activity, builder.position)
        flashbarView.addParent(flashbarContainerView)

        flashbarContainerView.attach(flashbarView)

        initializeContainerDecor()
        initializeBarDecor()

        flashbarContainerView.construct()
    }

    private fun initializeContainerDecor() {
        with(flashbarContainerView) {
            setDuration(builder.duration)
            setBarShownListener(builder.onBarShownListener)
            setBarDismissListener(builder.onBarDismissListener)
            setBarDismissOnTapOutside(builder.barDismissOnTapOutside)
            setModalOverlayColor(builder.modalOverlayColor)
            setModalOverlayBlockable(builder.modalOverlayBlockable)
            setVibrationTargets(builder.vibrationTargets)

            setEnterAnimation(builder.enterAnimation!!)
            setExitAnimation(builder.exitAnimation!!)
            enableSwipeToDismiss(builder.enableSwipeToDismiss)
        }
    }

    private fun initializeBarDecor() {
        with(flashbarView) {
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
            setActionTapListener(builder.onActionTapListener)

            showIcon(builder.showIcon)
            setIconDrawable(builder.iconDrawable)
            setIconBitmap(builder.iconBitmap)
            setIconColorFilter(builder.iconColorFilter, builder.iconColorFilterMode)
        }
    }

    class Builder(internal var activity: Activity) {

        internal var position: FlashbarPosition = TOP
        internal var backgroundColor: Int? = null
        internal var backgroundDrawable: Drawable? = null
        internal var onBarTapListener: OnBarTapListener? = null
        internal var duration: Long = DURATION_INDEFINITE
        internal var onBarShownListener: OnBarShowListener? = null
        internal var onBarDismissListener: OnBarDismissListener? = null
        internal var barDismissOnTapOutside: Boolean = false
        internal var modalOverlayColor: Int? = null
        internal var modalOverlayBlockable: Boolean = false
        internal var castShadow: Boolean = true
        internal var shadowStrength: Int? = null
        internal var enableSwipeToDismiss: Boolean = false
        internal var vibrationTargets: List<Vibration> = emptyList()

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

        internal var enterAnimation: FlashAnim? = null
        internal var exitAnimation: FlashAnim? = null

        fun position(position: FlashbarPosition) = apply { this.position = position }

        fun backgroundDrawable(drawable: Drawable) = apply { this.backgroundDrawable = drawable }

        fun backgroundDrawable(@DrawableRes drawableId: Int) = apply {
            this.backgroundDrawable = ContextCompat.getDrawable(activity, drawableId)
        }

        fun backgroundColor(@ColorInt color: Int) = apply { this.backgroundColor = color }

        fun backgroundColorRes(@ColorRes colorId: Int) = apply {
            this.backgroundColor = ContextCompat.getColor(activity, colorId)
        }

        fun barTapListener(listener: OnBarTapListener) = apply {
            this.onBarTapListener = listener
        }

        fun duration(milliseconds: Long) = apply {
            require(milliseconds > 0) { "Duration can not be negative" }
            this.duration = milliseconds
        }

        fun barShownListener(listener: OnBarShowListener) = apply {
            this.onBarShownListener = listener
        }

        fun barDismissListener(listener: OnBarDismissListener) = apply {
            this.onBarDismissListener = listener
        }

        fun dismissOnTapOutside() = apply {
            this.barDismissOnTapOutside = true
        }

        fun modalOverlayColor(@ColorInt color: Int) = apply { this.modalOverlayColor = color }

        fun modalOverlayColorRes(@ColorRes colorId: Int) = apply {
            this.modalOverlayColor = ContextCompat.getColor(activity, colorId)
        }

        fun modalOverlayBlockable(blockable: Boolean) = apply {
            this.modalOverlayBlockable = blockable
        }

        fun castShadow(shadow: Boolean) = apply {
            this.castShadow = shadow
        }

        fun shadowStrength(strength: Int) = apply {
            require(strength > 0) { "Shadow strength can not be negative" }
            this.shadowStrength = strength
        }

        fun enterAnimation(animation: FlashAnim) = apply { this.enterAnimation = animation }

        fun exitAnimation(animation: FlashAnim) = apply { this.exitAnimation = animation }

        fun enableSwipeToDismiss() = apply {
            this.enableSwipeToDismiss = true
        }

        fun vibrateOn(vararg vibrate: Vibration) = apply {
            this.vibrationTargets = vibrate.toList()
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
            configureDefaultAnim()

            val flashbar = Flashbar(this)
            flashbar.construct()
            return flashbar
        }

        fun show() = build().show()

        private fun configureDefaultAnim() {
            if (enterAnimation == null) {
                enterAnimation = when (position) {
                    TOP -> FlashAnim.with(activity).enter(FlashAnim.Position.TOP).build()
                    BOTTOM -> FlashAnim.with(activity).enter(FlashAnim.Position.BOTTOM).build()
                }
            }

            if (exitAnimation == null) {
                exitAnimation = when (position) {
                    TOP -> FlashAnim.with(activity).exit(FlashAnim.Position.TOP).build()
                    BOTTOM -> FlashAnim.with(activity).exit(FlashAnim.Position.BOTTOM).build()
                }
            }
        }
    }

    enum class FlashbarPosition {
        TOP,
        BOTTOM
    }

    enum class FlashbarDismissEvent {
        TIMEOUT,
        MANUAL,
        TAP_OUTSIDE,
        SWIPE
    }

    enum class Vibration {
        SHOW,
        DISMISS
    }

    companion object {
        const val DURATION_SHORT = 1000L
        const val DURATION_LONG = 2500L
        const val DURATION_INDEFINITE = -1L
    }

    interface OnActionTapListener {
        fun onActionTapped(bar: Flashbar)
    }

    interface OnBarDismissListener {
        fun onDismissing(bar: Flashbar, isSwiping: Boolean)
        fun onDismissed(bar: Flashbar, event: FlashbarDismissEvent)
    }

    interface OnBarShowListener {
        fun onShowing(bar: Flashbar)
        fun onShown(bar: Flashbar)
    }

    interface OnBarTapListener {
        fun onBarTapped(flashbar: Flashbar)
    }
}
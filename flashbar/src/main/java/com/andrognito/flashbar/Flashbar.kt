package com.andrognito.flashbar

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.text.Spanned
import com.andrognito.flashbar.FlashAnim.Position
import com.andrognito.flashbar.Flashbar.FlashbarPosition.BOTTOM
import com.andrognito.flashbar.Flashbar.FlashbarPosition.TOP

class Flashbar private constructor(private var builder: Builder) {

    private lateinit var flashbarContainerView: FlashbarContainerView
    private lateinit var flashbarView: FlashbarView

    /**
     * Shows a flashbar
     */
    fun show() {
        flashbarContainerView.show(builder.activity)
    }

    /**
     * Dismisses a flashbar
     */
    fun dismiss() {
        flashbarContainerView.dismiss()
    }

    /**
     * Returns true/false depending on whether the flashbar is showing or not
     * This represents the partial appearance of the flashbar
     */
    fun isShowing() = flashbarContainerView.isBarShowing()

    /**
     * Returns true/false depending on whether the flashbar has been completely shown or not
     * This represents the complete appearance of the flashbar
     */
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
            setOnTapOutsideListener(builder.onTapOutsideListener)
            setModalOverlayColor(builder.modalOverlayColor)
            setModalOverlayBlockable(builder.modalOverlayBlockable)
            setVibrationTargets(builder.vibrationTargets)
            setIconAnimation(builder.iconAnimation)

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

            setProgressPosition(builder.progressPosition)
            setProgressTint(builder.progressTint, builder.progressTintMode, builder.progressPosition)
        }
    }

    class Builder(internal var activity: Activity) {

        internal var position: FlashbarPosition = TOP
        internal var backgroundColor: Int? = null
        internal var backgroundDrawable: Drawable? = null
        internal var duration: Long = DURATION_INDEFINITE
        internal var onBarTapListener: OnBarTapListener? = null
        internal var onBarShownListener: OnBarShowListener? = null
        internal var onBarDismissListener: OnBarDismissListener? = null
        internal var barDismissOnTapOutside: Boolean = false
        internal var onTapOutsideListener: OnTapOutsideListener? = null
        internal var modalOverlayColor: Int? = null
        internal var modalOverlayBlockable: Boolean = false
        internal var castShadow: Boolean = true
        internal var shadowStrength: Int? = null
        internal var enableSwipeToDismiss: Boolean = false
        internal var vibrationTargets: List<Vibration> = emptyList()
        internal var progressPosition: ProgressPosition? = null

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
        internal var iconAnimation: FlashAnim? = null
        internal var progressTint: Int? = null
        internal var progressTintMode: PorterDuff.Mode? = null

        internal var enterAnimation: FlashAnim? = null
        internal var exitAnimation: FlashAnim? = null

        /**
         * Specifies the position from where the flashbar will be shown (top/bottom)
         * Default position is TOP
         */
        fun position(position: FlashbarPosition) = apply { this.position = position }

        /**
         * Specifies the background drawable of the flashbar
         */
        fun backgroundDrawable(drawable: Drawable) = apply { this.backgroundDrawable = drawable }

        /**
         * Specifies the background drawable resource of the flashbar
         */
        fun backgroundDrawable(@DrawableRes drawableId: Int) = apply {
            this.backgroundDrawable = ContextCompat.getDrawable(activity, drawableId)
        }

        /**
         * Specifies the background color of the flashbar
         */
        fun backgroundColor(@ColorInt color: Int) = apply { this.backgroundColor = color }

        /**
         * Specifies the background color resource of the flashbar
         */
        fun backgroundColorRes(@ColorRes colorId: Int) = apply {
            this.backgroundColor = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Sets listener to receive tap events on the surface of the bar
         */
        fun barTapListener(listener: OnBarTapListener) = apply {
            this.onBarTapListener = listener
        }

        /**
         * Specifies the duration for which the flashbar will be visible
         * By default, the duration is infinite
         */
        fun duration(milliseconds: Long) = apply {
            require(milliseconds > 0) { "Duration can not be negative" }
            this.duration = milliseconds
        }

        /**
         * Sets listener to receive bar showing/shown events
         */
        fun barShownListener(listener: OnBarShowListener) = apply {
            this.onBarShownListener = listener
        }

        /**
         * Sets listener to receive bar dismissing/dismissed events
         */
        fun barDismissListener(listener: OnBarDismissListener) = apply {
            this.onBarDismissListener = listener
        }

        /**
         * Sets listener to receive tap events outside flashbar surface
         */
        fun tapOutsideListener(listener: OnTapOutsideListener) = apply {
            this.onTapOutsideListener = listener
        }

        /**
         * Dismisses the bar on being tapped outside
         */
        fun dismissOnTapOutside() = apply {
            this.barDismissOnTapOutside = true
        }

        /**
         * Specifies modal overlay color
         * Modal overlay is automatically shown if color is set
         */
        fun modalOverlayColor(@ColorInt color: Int) = apply { this.modalOverlayColor = color }

        /**
         * Specifies modal overlay color resource
         * Modal overlay is automatically shown if color is set
         */
        fun modalOverlayColorRes(@ColorRes colorId: Int) = apply {
            this.modalOverlayColor = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Specifies if modal overlay is blockable and should comsume touch events
         */
        fun modalOverlayBlockable(blockable: Boolean) = apply {
            this.modalOverlayBlockable = blockable
        }

        /**
         * Specifies if the flashbar should cast shadow
         */
        fun castShadow(shadow: Boolean) = apply {
            this.castShadow = shadow
        }

        /**
         * Specifies the strength of the shadow
         */
        fun shadowStrength(strength: Int) = apply {
            require(strength > 0) { "Shadow strength can not be negative" }
            this.shadowStrength = strength
        }

        /**
         * Specifies the enter animation of the flashbar
         */
        fun enterAnimation(anim: FlashAnim) = apply { this.enterAnimation = anim }

        /**
         * Specifies the exit animation of the flashbar
         */
        fun exitAnimation(anim: FlashAnim) = apply { this.exitAnimation = anim }

        /**
         * Enables swipe-to-dismiss for the flashbar
         */
        fun enableSwipeToDismiss() = apply {
            this.enableSwipeToDismiss = true
        }

        /**
         * Specifies whether the device should vibrate during flashbar enter/exit/both
         */
        fun vibrateOn(vararg vibrate: Vibration) = apply {
            this.vibrationTargets = vibrate.toList()
        }

        /**
         * Specifies the title string
         */
        fun title(title: String) = apply { this.title = title }

        /**
         * Specifies the title string res
         */
        fun title(@StringRes titleId: Int) = apply { this.title = activity.getString(titleId) }

        /**
         * Specifies the title span
         */
        fun title(title: Spanned) = apply { this.titleSpanned = title }

        /**
         * Specifies the title typeface
         */
        fun titleTypeface(typeface: Typeface) = apply { this.titleTypeface = typeface }

        /**
         * Specifies the title size (in pixels)
         */
        fun titleSizeInPx(size: Float) = apply { this.titleSizeInPx = size }

        /**
         * Specifies the title size (in sp)
         */
        fun titleSizeInSp(size: Float) = apply { this.titleSizeInSp = size }

        /**
         * Specifies the title color
         */
        fun titleColor(color: Int) = apply { this.titleColor = color }

        /**
         * Specifies the title color resource
         */
        fun titleColorRes(colorId: Int) = apply {
            this.titleColor = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Specifies the title appearance
         */
        fun titleAppearance(@StyleRes appearance: Int) = apply {
            this.titleAppearance = appearance
        }

        /**
         * Specifies the message string
         */
        fun message(message: String) = apply { this.message = message }

        /**
         * Specifies the message string resource
         */
        fun message(@StringRes messageId: Int) = apply {
            this.message = activity.getString(messageId)
        }

        /**
         * Specifies the message string span
         */
        fun message(message: Spanned) = apply { this.messageSpanned = message }

        /**
         * Specifies the message typeface
         */
        fun messageTypeface(typeface: Typeface) = apply { this.messageTypeface = typeface }

        /**
         * Specifies the message size (in pixels)
         */
        fun messageSizeInPx(size: Float) = apply { this.messageSizeInPx = size }

        /**
         * Specifies the message size (in sp)
         */
        fun messageSizeInSp(size: Float) = apply { this.messageSizeInSp = size }

        /**
         * Specifies the message color
         */
        fun messageColor(color: Int) = apply { this.messageColor = color }

        /**
         * Specifies the message color resource
         */
        fun messageColorRes(colorId: Int) = apply {
            this.messageColor = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Specifies the message appearance
         */
        fun messageAppearance(@StyleRes appearance: Int) = apply {
            this.titleAppearance = appearance
        }

        /**
         * Specifies the action text string
         */
        fun actionText(text: String) = apply {
            require(progressPosition != ProgressPosition.RIGHT,
                    { "Cannot show action button if right progress is set" })
            this.actionText = text
        }

        /**
         * Specifies the action text string resource
         */
        fun actionText(@StringRes actionTextId: Int) = apply {
            actionText(activity.getString(actionTextId))
        }

        /**
         * Specifies the action text string span
         */
        fun actionText(actionText: Spanned) = apply { this.actionTextSpanned = actionText }

        /**
         * Specifies the action text typeface
         */
        fun actionTextTypeface(typeface: Typeface) = apply { this.actionTextTypeface = typeface }

        /**
         * Specifies the action text size (in pixels)
         */
        fun actionTextSizeInPx(size: Float) = apply { this.actionTextSizeInPx = size }

        /**
         * Specifies the action text size (in sp)
         */
        fun actionTextSizeInSp(size: Float) = apply { this.actionTextSizeInSp = size }

        /**
         * Specifies the action text color
         */
        fun actionTextColor(color: Int) = apply { this.actionTextColor = color }

        /**
         * Specifies the action text color resource
         */
        fun actionTextColorRes(colorId: Int) = apply {
            this.actionTextColor = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Specifies the action text appearance
         */
        fun actionTextAppearance(@StyleRes appearance: Int) = apply {
            this.actionTextAppearance = appearance
        }

        /**
         * Sets listener to receive tap events on the action
         */
        fun actionTapListener(onActionTapListener: OnActionTapListener) = apply {
            this.onActionTapListener = onActionTapListener
        }

        /**
         * Specifies if the icon should be shown
         */
        fun showIcon(showIcon: Boolean) = apply {
            require(progressPosition != ProgressPosition.LEFT,
                    { "Cannot show icon if left progress is set" })
            this.showIcon = showIcon
        }

        /**
         * Specifies the icon drawable
         */
        fun icon(icon: Drawable) = apply { this.iconDrawable = icon }

        /**
         * Specifies the icon drawable resource
         */
        fun icon(@DrawableRes iconId: Int) = apply {
            this.iconDrawable = ContextCompat.getDrawable(activity, iconId)
        }

        /**
         * Specifies the icon bitmap
         */
        fun icon(bitmap: Bitmap) = apply { this.iconBitmap = bitmap }

        /**
         * Specifies the icon color filter and mode
         */
        fun iconColorFilter(@ColorInt color: Int, mode: PorterDuff.Mode? = null) = apply {
            this.iconColorFilter = color
            this.iconColorFilterMode = mode
        }

        /**
         * Specifies the icon color filter resource and mode
         */
        fun iconColorFilterRes(@ColorRes colorId: Int, mode: PorterDuff.Mode? = null) = apply {
            this.iconColorFilter = ContextCompat.getColor(activity, colorId)
            this.iconColorFilterMode = mode
        }

        /**
         * Specifies the icon animation
         */
        fun iconAnimation(animation: FlashAnim) = apply { this.iconAnimation = animation }

        /**
         * Specifies the position in which the indeterminate progress is shown (left/right)
         */
        fun showProgress(position: ProgressPosition) = apply {
            this.progressPosition = position

            if (progressPosition == ProgressPosition.LEFT && showIcon) {
                throw IllegalArgumentException("Cannot show progress at left if icon is already set")
            }

            if (progressPosition == ProgressPosition.RIGHT && actionText != null) {
                throw IllegalArgumentException("Cannot show progress at right if action button is already set")
            }
        }

        /**
         * Specifies the indeterminate progress tint
         */
        fun progressTint(@ColorInt color: Int, mode: PorterDuff.Mode? = null) = apply {
            this.progressTint = color
            this.progressTintMode = mode
        }

        /**
         * Specifies the indeterminate progress tint resource
         */
        fun progressTintRes(@ColorRes colorId: Int, mode: PorterDuff.Mode? = null) = apply {
            this.progressTint = ContextCompat.getColor(activity, colorId)
            this.progressTintMode = mode
        }

        /**
         * Builds a flashbar instance
         */
        fun build(): Flashbar {
            configureDefaultAnim()

            val flashbar = Flashbar(this)
            flashbar.construct()
            return flashbar
        }

        /**
         * Shows the flashbar
         */
        fun show() = build().show()

        private fun configureDefaultAnim() {
            if (enterAnimation == null) {
                enterAnimation = when (position) {
                    TOP -> FlashAnim.with(activity).animateBar().enterFrom(Position.TOP).build()
                    BOTTOM -> FlashAnim.with(activity).animateBar().enterFrom(Position.BOTTOM).build()
                }
            }

            if (exitAnimation == null) {
                exitAnimation = when (position) {
                    TOP -> FlashAnim.with(activity).animateBar().exitFrom(Position.TOP).build()
                    BOTTOM -> FlashAnim.with(activity).animateBar().exitFrom(Position.BOTTOM).build()
                }
            }
        }
    }

    companion object {
        const val DURATION_SHORT = 1000L
        const val DURATION_LONG = 2500L
        const val DURATION_INDEFINITE = -1L
    }

    enum class FlashbarPosition { TOP, BOTTOM }

    enum class FlashbarDismissEvent {
        TIMEOUT,
        MANUAL,
        TAP_OUTSIDE,
        SWIPE
    }

    enum class Vibration { SHOW, DISMISS }

    enum class ProgressPosition { LEFT, RIGHT }

    interface OnActionTapListener {
        fun onActionTapped(bar: Flashbar)
    }

    interface OnBarDismissListener {
        fun onDismissing(bar: Flashbar, isSwiping: Boolean)
        fun onDismissed(bar: Flashbar, event: FlashbarDismissEvent)
    }

    interface OnTapOutsideListener {
        fun onTap(bar: Flashbar)
    }

    interface OnBarShowListener {
        fun onShowing(bar: Flashbar)
        fun onShown(bar: Flashbar)
    }

    interface OnBarTapListener {
        fun onBarTapped(flashbar: Flashbar)
    }
}
package com.andrognito.flashbar

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.text.Spanned
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.Animation
import android.widget.RelativeLayout
import com.andrognito.flashbar.Flashbar.Companion.DURATION_INDEFINITE
import com.andrognito.flashbar.Flashbar.FlashbarDismissEvent
import com.andrognito.flashbar.Flashbar.FlashbarDismissEvent.MANUAL
import com.andrognito.flashbar.Flashbar.FlashbarDismissEvent.TIMEOUT
import com.andrognito.flashbar.Flashbar.FlashbarPosition
import com.andrognito.flashbar.listeners.OnActionTapListener
import com.andrognito.flashbar.listeners.OnBarDismissListener
import com.andrognito.flashbar.listeners.OnBarShowListener
import com.andrognito.flashbar.listeners.OnBarTapListener
import com.andrognito.flashbar.utils.NavigationBarPosition
import com.andrognito.flashbar.utils.getNavigationBarPosition
import com.andrognito.flashbar.utils.getNavigationBarSizeInPx
import com.andrognito.flashbar.utils.getRootView

/**
 * Container view matching the height and width of the parent to hold a FlashbarView.
 * It will occupy the entire screens size but will be completely transparent. The
 * FlashbarView inside is the only visible component in it.
 */
internal class FlashbarContainerView(context: Context) : RelativeLayout(context) {

    private lateinit var flashbarView: FlashbarView
    internal lateinit var parentFlashbar: Flashbar

    private lateinit var enterAnimation: Animation
    private lateinit var exitAnimation: Animation

    private var onBarShowListener: OnBarShowListener? = null
    private var onBarDismissListener: OnBarDismissListener? = null

    private var duration = DURATION_INDEFINITE
    private var isBarShowing = false
    private var isBarShown = false
    private var isBarDismissing = false

    internal fun construct(activity: Activity, position: FlashbarPosition) {
        flashbarView = FlashbarView(activity)
        flashbarView.addParent(this)

        flashbarView.adjustWitPositionAndOrientation(activity, position)
        addView(flashbarView)
    }

    internal fun addParent(flashbar: Flashbar) {
        this.parentFlashbar = flashbar
    }

    internal fun adjustPositionAndOrientation(activity: Activity) {
        val flashbarContainerViewLp = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

        val navigationBarPosition = activity.getNavigationBarPosition()
        val navigationBarSize = activity.getNavigationBarSizeInPx()

        when (navigationBarPosition) {
            NavigationBarPosition.LEFT -> flashbarContainerViewLp.leftMargin = navigationBarSize
            NavigationBarPosition.RIGHT -> flashbarContainerViewLp.rightMargin = navigationBarSize
            NavigationBarPosition.BOTTOM -> flashbarContainerViewLp.bottomMargin = navigationBarSize
        }

        layoutParams = flashbarContainerViewLp
    }

    internal fun show(activity: Activity) {
        if (isBarShowing || isBarShown) {
            return
        }

        val activityRootView = activity.getRootView()
        activityRootView?.addView(this)

        enterAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation) {
                isBarShowing = true
                onBarShowListener?.onShowing(parentFlashbar)
            }

            override fun onAnimationEnd(animation: Animation) {
                isBarShowing = false
                isBarShown = true
                onBarShowListener?.onShown(parentFlashbar)
            }

            override fun onAnimationRepeat(animation: Animation) {
                // NO-OP
            }
        })

        flashbarView.startAnimation(enterAnimation)
        handleDismiss()
    }

    internal fun dismiss() {
        dismissInternal(MANUAL)
    }

    internal fun isBarShowing() = isBarShowing

    internal fun isBarShown() = isBarShown

    internal fun setBarBackgroundColor(@ColorInt color: Int?) {
        flashbarView.setBarBackgroundColor(color)
    }

    internal fun setBarTapListener(listener: OnBarTapListener?) {
        flashbarView.setBarTapListener(listener)
    }

    internal fun setBarBackgroundDrawable(drawable: Drawable?) {
        flashbarView.setBarBackground(drawable)
    }

    internal fun setDuration(duration: Long) {
        this.duration = duration
    }

    internal fun setBarShownListener(listener: OnBarShowListener?) {
        this.onBarShowListener = listener
    }

    internal fun setBarDismissListener(listener: OnBarDismissListener?) {
        this.onBarDismissListener = listener
    }

    internal fun setEnterAnimation(animation: Animation) {
        this.enterAnimation = animation
    }

    internal fun setExitAnimation(animation: Animation) {
        this.exitAnimation = animation
    }

    internal fun setTitle(title: String?) {
        flashbarView.setTitle(title)
    }

    internal fun setTitleSpanned(title: Spanned?) {
        flashbarView.setTitleSpanned(title)
    }

    internal fun setTitleTypeface(typeface: Typeface?) {
        flashbarView.setTitleTypeface(typeface)
    }

    internal fun setTitleSizeInPx(size: Float?) {
        flashbarView.setTitleSizeInPx(size)
    }

    internal fun setTitleSizeInSp(size: Float?) {
        flashbarView.setTitleSizeInSp(size)
    }

    internal fun setTitleColor(color: Int?) {
        flashbarView.setTitleColor(color)
    }

    internal fun setTitleAppearance(titleAppearance: Int?) {
        flashbarView.setTitleAppearance(titleAppearance)
    }

    internal fun setMessage(message: String?) {
        flashbarView.setMessage(message)
    }

    internal fun setMessageSpanned(message: Spanned?) {
        flashbarView.setMessageSpanned(message)
    }

    internal fun setMessageTypeface(typeface: Typeface?) {
        flashbarView.setMessageTypeface(typeface)
    }

    internal fun setMessageSizeInPx(size: Float?) {
        flashbarView.setMessageSizeInPx(size)
    }

    internal fun setMessageSizeInSp(size: Float?) {
        flashbarView.setMessageSizeInSp(size)
    }

    internal fun setMessageColor(color: Int?) {
        flashbarView.setMessageColor(color)
    }

    internal fun setMessageAppearance(messageAppearance: Int?) {
        flashbarView.setMessageAppearance(messageAppearance)
    }

    internal fun setActionText(text: String?) {
        flashbarView.setActionText(text)
    }

    internal fun setActionTextSpanned(text: Spanned?) {
        flashbarView.setActionTextSpanned(text)
    }

    internal fun setActionTextTypeface(typeface: Typeface?) {
        flashbarView.setActionTextTypeface(typeface)
    }

    internal fun setActionTextSizeInPx(size: Float?) {
        flashbarView.setActionTextSizeInPx(size)
    }

    internal fun setActionTextSizeInSp(size: Float?) {
        flashbarView.setActionTextSizeInSp(size)
    }

    internal fun setActionTextColor(color: Int?) {
        flashbarView.setActionTextColor(color)
    }

    internal fun setActionTextAppearance(appearance: Int?) {
        flashbarView.setActionTextAppearance(appearance)
    }

    internal fun setActionOnTapListener(listener: OnActionTapListener?) {
        flashbarView.setActionTapListener(listener)
    }

    internal fun showIcon(showIcon: Boolean) {
        flashbarView.showIcon(showIcon)
    }

    internal fun setIconDrawable(icon: Drawable?) {
        flashbarView.setIconDrawable(icon)
    }

    internal fun setIconBitmap(bitmap: Bitmap?) {
        flashbarView.setIconBitmap(bitmap)
    }

    internal fun setIconColorFilter(colorFilter: Int?, filterMode: PorterDuff.Mode?) {
        flashbarView.setIconFilter(colorFilter, filterMode)
    }

    private fun handleDismiss() {
        if (duration != DURATION_INDEFINITE) {
            postDelayed({ dismissInternal(TIMEOUT) }, duration)
        }
    }

    private fun dismissInternal(event: FlashbarDismissEvent) {
        if (isBarDismissing || isBarShowing || !isBarShown) {
            return
        }

        exitAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {
                isBarDismissing = true
                onBarDismissListener?.onDismissing(parentFlashbar)
            }

            override fun onAnimationEnd(animation: Animation?) {
                isBarDismissing = false
                isBarShown = false

                onBarDismissListener?.onDismissed(parentFlashbar, event)

                // Removing container after animation end
                post { (parent as? ViewGroup)?.removeView(this@FlashbarContainerView) }
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // NO-OP
            }
        })
        flashbarView.startAnimation(exitAnimation)
    }
}

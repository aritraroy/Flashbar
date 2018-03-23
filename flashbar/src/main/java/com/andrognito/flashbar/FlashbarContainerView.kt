package com.andrognito.flashbar

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.Animation
import android.widget.RelativeLayout
import com.andrognito.flashbar.Flashbar.Companion.DURATION_INDEFINITE
import com.andrognito.flashbar.Flashbar.FlashbarDismissEvent
import com.andrognito.flashbar.Flashbar.FlashbarDismissEvent.*
import com.andrognito.flashbar.Flashbar.Vibration.DISMISS
import com.andrognito.flashbar.Flashbar.Vibration.SHOW
import com.andrognito.flashbar.util.NavigationBarPosition.*
import com.andrognito.flashbar.util.getNavigationBarPosition
import com.andrognito.flashbar.util.getNavigationBarSizeInPx
import com.andrognito.flashbar.util.getRootView
import com.andrognito.flashbar.view.SwipeDismissTouchListener.DismissCallbacks


/**
 * Container view matching the height and width of the parent to hold a FlashbarView.
 * It will occupy the entire screens size but will be completely transparent. The
 * FlashbarView inside is the only visible component in it.
 */
internal class FlashbarContainerView(context: Context) : RelativeLayout(context), DismissCallbacks {

    private lateinit var flashbarView: FlashbarView
    internal lateinit var parentFlashbar: Flashbar

    private lateinit var enterAnimation: Animation
    private lateinit var exitAnimation: Animation
    private lateinit var vibrationTargets: List<Flashbar.Vibration>

    private var onBarShowListener: Flashbar.OnBarShowListener? = null
    private var onBarDismissListener: Flashbar.OnBarDismissListener? = null
    private var modalOverlayColor: Int? = null

    private var duration = DURATION_INDEFINITE
    private var isBarShowing = false
    private var isBarShown = false
    private var isBarDismissing = false
    private var barDismissOnTapOutside: Boolean = false
    private var modalOverlayBlockable: Boolean = false

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when (action) {
            ACTION_DOWN -> {
                if (barDismissOnTapOutside) {
                    val rect = Rect()
                    flashbarView.getHitRect(rect)

                    // Checks if the tap was outside the bar
                    if (!rect.contains(event.x.toInt(), event.y.toInt())) {
                        dismissInternal(TAP_OUTSIDE)
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    override fun onSwipe(isSwiping: Boolean) {
        isBarDismissing = isSwiping
        if (isSwiping) {
            onBarDismissListener?.onDismissing(parentFlashbar, true)
        }
    }

    override fun onDismiss(view: View) {
        (parent as? ViewGroup)?.removeView(this@FlashbarContainerView)
        isBarShown = false

        if (vibrationTargets.contains(DISMISS)) {
            performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        }

        onBarDismissListener?.onDismissed(parentFlashbar, SWIPE)
    }

    internal fun attach(flashbarView: FlashbarView) {
        this.flashbarView = flashbarView
    }

    internal fun construct() {
        isHapticFeedbackEnabled = true

        if (modalOverlayColor != null) {
            setBackgroundColor(modalOverlayColor!!)

            if (modalOverlayBlockable) {
                isClickable = true
                isFocusable = true
            }
        }

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
            LEFT -> flashbarContainerViewLp.leftMargin = navigationBarSize
            RIGHT -> flashbarContainerViewLp.rightMargin = navigationBarSize
            BOTTOM -> flashbarContainerViewLp.bottomMargin = navigationBarSize
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

                if (vibrationTargets.contains(SHOW)) {
                    performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }

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

    internal fun setDuration(duration: Long) {
        this.duration = duration
    }

    internal fun setBarShownListener(listener: Flashbar.OnBarShowListener?) {
        this.onBarShowListener = listener
    }

    internal fun setBarDismissListener(listener: Flashbar.OnBarDismissListener?) {
        this.onBarDismissListener = listener
    }

    internal fun setBarDismissOnTapOutside(dismiss: Boolean) {
        this.barDismissOnTapOutside = dismiss
    }

    internal fun setModalOverlayColor(color: Int?) {
        this.modalOverlayColor = color
    }

    internal fun setModalOverlayBlockable(blockable: Boolean) {
        this.modalOverlayBlockable = blockable
    }

    internal fun setEnterAnimation(anim: FlashAnim) {
        this.enterAnimation = anim.getAnimation()
        this.flashbarView.setEnterAnimation(anim)
    }

    internal fun setExitAnimation(anim: FlashAnim) {
        this.exitAnimation = anim.getAnimation()
    }

    internal fun enableSwipeToDismiss(enable: Boolean) {
        this.flashbarView.enableSwipeToDismiss(enable, this)
    }

    internal fun setVibrationTargets(targets: List<Flashbar.Vibration>) {
        this.vibrationTargets = targets
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
                onBarDismissListener?.onDismissing(parentFlashbar, false)
            }

            override fun onAnimationEnd(animation: Animation?) {
                isBarDismissing = false
                isBarShown = false

                if (vibrationTargets.contains(DISMISS)) {
                    performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                }

                onBarDismissListener?.onDismissed(parentFlashbar, event)

                post { (parent as? ViewGroup)?.removeView(this@FlashbarContainerView) }
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // NO-OP
            }
        })
        flashbarView.startAnimation(exitAnimation)
    }
}

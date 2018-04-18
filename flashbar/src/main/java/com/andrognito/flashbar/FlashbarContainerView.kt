package com.andrognito.flashbar

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.HapticFeedbackConstants.VIRTUAL_KEY
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.RelativeLayout
import com.andrognito.flashbar.Flashbar.Companion.DURATION_INDEFINITE
import com.andrognito.flashbar.Flashbar.DismissEvent
import com.andrognito.flashbar.Flashbar.DismissEvent.*
import com.andrognito.flashbar.Flashbar.Vibration.DISMISS
import com.andrognito.flashbar.SwipeDismissTouchListener.DismissCallbacks
import com.andrognito.flashbar.util.NavigationBarPosition.*
import com.andrognito.flashbar.util.afterMeasured
import com.andrognito.flashbar.util.getNavigationBarPosition
import com.andrognito.flashbar.util.getNavigationBarSizeInPx
import com.andrognito.flashbar.util.getRootView

/**
 * Container view matching the height and width of the parent to hold a FlashbarView.
 * It will occupy the entire screens size but will be completely transparent. The
 * FlashbarView inside is the only visible component in it.
 */
internal class FlashbarContainerView(context: Context)
    : RelativeLayout(context), DismissCallbacks {

    internal lateinit var parentFlashbar: Flashbar

    private lateinit var flashbarView: FlashbarView

    private lateinit var enterAnimBuilder: FlashAnimBuilder
    private lateinit var exitAnimBuilder: FlashAnimBuilder
    private lateinit var vibrationTargets: List<Flashbar.Vibration>

    private var onBarShowListener: Flashbar.OnBarShowListener? = null
    private var onBarDismissListener: Flashbar.OnBarDismissListener? = null
    private var onTapOutsideListener: Flashbar.OnTapOutsideListener? = null
    private var modalOverlayColor: Int? = null
    private var iconAnimator: Animator? = null

    private var duration = DURATION_INDEFINITE
    private var isBarShowing = false
    private var isBarShown = false
    private var isBarDismissing = false
    private var barDismissOnTapOutside: Boolean = false
    private var modalOverlayBlockable: Boolean = false

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            ACTION_DOWN -> {
                val rect = Rect()
                flashbarView.getHitRect(rect)

                // Checks if the tap was outside the bar
                if (!rect.contains(event.x.toInt(), event.y.toInt())) {
                    onTapOutsideListener?.onTap(parentFlashbar)

                    if (barDismissOnTapOutside) {
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

        flashbarView.stopIconAnimation()

        if (vibrationTargets.contains(DISMISS)) {
            performHapticFeedback(VIRTUAL_KEY)
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

    internal fun adjustOrientation(activity: Activity) {
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
        if (isBarShowing || isBarShown) return

        val activityRootView = activity.getRootView() ?: return

        // Only add the view to the parent once
        if (this.parent == null) activityRootView.addView(this)

        activityRootView.afterMeasured {
            val enterAnim = enterAnimBuilder.buildWith(flashbarView)
            enterAnim.start(object : FlashAnim.AnimationListener {
                override fun onStart() {
                    isBarShowing = true
                    onBarShowListener?.onShowing(parentFlashbar)
                }

                override fun onUpdate(progress: Float) {
                    onBarShowListener?.onShowProgress(parentFlashbar, progress)
                }

                override fun onStop() {
                    isBarShowing = false
                    isBarShown = true

                    flashbarView.startIconAnimation(iconAnimator)

                    if (vibrationTargets.contains(Flashbar.Vibration.SHOW)) {
                        performHapticFeedback(VIRTUAL_KEY)
                    }

                    onBarShowListener?.onShown(parentFlashbar)
                }
            })

            handleDismiss()
        }
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

    internal fun setOnTapOutsideListener(listener: Flashbar.OnTapOutsideListener?) {
        this.onTapOutsideListener = listener
    }

    internal fun setModalOverlayColor(color: Int?) {
        this.modalOverlayColor = color
    }

    internal fun setModalOverlayBlockable(blockable: Boolean) {
        this.modalOverlayBlockable = blockable
    }

    internal fun setEnterAnim(builder: FlashAnimBuilder) {
        this.enterAnimBuilder = builder
    }

    internal fun setExitAnim(builder: FlashAnimBuilder) {
        this.exitAnimBuilder = builder
    }

    internal fun enableSwipeToDismiss(enable: Boolean) {
        this.flashbarView.enableSwipeToDismiss(enable, this)
    }

    internal fun setVibrationTargets(targets: List<Flashbar.Vibration>) {
        this.vibrationTargets = targets
    }

    internal fun setIconAnimator(animator: Animator?) {
        this.iconAnimator = animator
    }

    private fun handleDismiss() {
        if (duration != DURATION_INDEFINITE) {
            postDelayed({ dismissInternal(TIMEOUT) }, duration)
        }
    }

    private fun dismissInternal(event: DismissEvent) {
        if (isBarDismissing || isBarShowing || !isBarShown) {
            return
        }

        val exitAnim = exitAnimBuilder.buildWith(flashbarView)
        exitAnim.start(object : FlashAnim.AnimationListener {
            override fun onStart() {
                isBarDismissing = true
                onBarDismissListener?.onDismissing(parentFlashbar, false)
            }

            override fun onUpdate(progress: Float) {
                onBarDismissListener?.onDismissProgress(parentFlashbar, progress)
            }

            override fun onStop() {
                isBarDismissing = false
                isBarShown = false

                if (vibrationTargets.contains(DISMISS)) {
                    performHapticFeedback(VIRTUAL_KEY)
                }

                onBarDismissListener?.onDismissed(parentFlashbar, event)

                post { (parent as? ViewGroup)?.removeView(this@FlashbarContainerView) }
            }
        })
    }
}

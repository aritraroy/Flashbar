package com.andrognito.flashbar

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.support.annotation.InterpolatorRes
import android.view.animation.*
import com.andrognito.flashbar.FlashAnimBuilder.Direction.LEFT
import com.andrognito.flashbar.FlashAnimBuilder.Direction.RIGHT
import com.andrognito.flashbar.FlashAnimBuilder.Type.ENTER
import com.andrognito.flashbar.FlashAnimBuilder.Type.EXIT
import com.andrognito.flashbar.Flashbar.Gravity.BOTTOM
import com.andrognito.flashbar.Flashbar.Gravity.TOP

class FlashAnimBuilder(private val context: Context) {

    private val DEFAULT_DURATION = context.resources
            .getInteger(R.integer.default_animation_duration).toLong()
    private val DEFAULT_ALPHA_START = 0.2f
    private val DEFAULT_ALPHA_END = 1.0f

    private var duration = DEFAULT_DURATION
    private var type: Type? = null
    private var gravity: Flashbar.Gravity? = null
    private var interpolator: Interpolator? = null
    private var alpha: Boolean = false
    private var direction: Direction? = null

    /**
     * Specifies the duration for enter/exit animation
     * By default, the duration is 250 ms
     */
    fun duration(millis: Long) = apply {
        require(duration >= 0, { "Duration must not be negative" })
        this.duration = millis
    }

    /**
     * Slides bar from left as enter/exit animation
     */
    fun slideFromLeft() = apply {
        this.direction = LEFT
    }

    /**
     * Slides bar from right as enter/exit animation
     */
    fun slideFromRight() = apply {
        this.direction = RIGHT
    }

    /**
     * Adds accelerate interpolator to the enter/exit animation
     */
    fun accelerate() = apply {
        this.interpolator = AccelerateInterpolator()
    }

    /**
     * Adds decelerate interpolator to the enter/exit animation
     */
    fun decelerate() = apply {
        this.interpolator = DecelerateInterpolator()
    }

    /**
     * Adds accelerateDecelerate interpolator to the enter/exit animation
     */
    fun accelerateDecelerate() = apply {
        this.interpolator = AccelerateDecelerateInterpolator()
    }

    /**
     * Adds overshoot interpolator to the enter/exit animation
     */
    fun overshoot() = apply {
        this.interpolator = OvershootInterpolator()
    }

    /**
     * Adds anticipateOvershoot interpolator to the enter/exit animation
     */
    fun anticipateOvershoot() = apply {
        this.interpolator = AnticipateInterpolator()
    }

    /**
     * Adds custom interpolator to the enter/exit animation
     */
    fun interpolator(interpolator: Interpolator) = apply {
        this.interpolator = interpolator
    }

    /**
     * Adds custom interpolator resource to the enter/exit animation
     */
    fun interpolator(@InterpolatorRes id: Int) = apply {
        this.interpolator = AnimationUtils.loadInterpolator(context, id)
    }

    /**
     * Adds alpha transition to the enter/exit animation
     */
    fun alpha() = apply {
        this.alpha = true
    }

    internal fun enter() = apply {
        this.type = ENTER
    }

    internal fun exit() = apply {
        this.type = EXIT
    }

    internal fun fromTop() = apply {
        this.gravity = TOP
    }

    internal fun fromBottom() = apply {
        this.gravity = BOTTOM
    }

    internal fun buildWith(view: FlashbarView): FlashAnim {
        val compositeAnim = AnimatorSet()

        val translationAnim = ObjectAnimator()
        if (direction == null) {
            translationAnim.propertyName = "translationY"

            when (type!!) {
                ENTER -> when (gravity!!) {
                    TOP -> translationAnim.setFloatValues(-view.height.toFloat(), 0f)
                    BOTTOM -> translationAnim.setFloatValues(view.height.toFloat(), 0f)
                }
                EXIT -> when (gravity!!) {
                    TOP -> translationAnim.setFloatValues(0f, -view.height.toFloat())
                    BOTTOM -> translationAnim.setFloatValues(0f, view.height.toFloat())
                }
            }
        } else {
            translationAnim.propertyName = "translationX"

            when (type!!) {
                ENTER -> when (direction!!) {
                    LEFT -> translationAnim.setFloatValues(-view.width.toFloat(), 0f)
                    RIGHT -> translationAnim.setFloatValues(view.width.toFloat(), 0f)
                }
                EXIT -> when (direction!!) {
                    LEFT -> translationAnim.setFloatValues(0f, -view.width.toFloat())
                    RIGHT -> translationAnim.setFloatValues(0f, view.width.toFloat())
                }
            }
        }

        translationAnim.target = view

        val alphaAnim = ObjectAnimator()
        alphaAnim.propertyName = "alpha"
        alphaAnim.target = view
        if (alpha) {
            when (type!!) {
                ENTER -> alphaAnim.setFloatValues(DEFAULT_ALPHA_START, DEFAULT_ALPHA_END)
                EXIT -> alphaAnim.setFloatValues(DEFAULT_ALPHA_END, DEFAULT_ALPHA_START)
            }
            compositeAnim.playTogether(translationAnim, alphaAnim)
        } else {
            compositeAnim.playTogether(translationAnim)
        }

        compositeAnim.duration = duration
        compositeAnim.interpolator = interpolator
        return FlashAnim(translationAnim, compositeAnim)
    }

    enum class Type { ENTER, EXIT }
    enum class Direction { LEFT, RIGHT }
}
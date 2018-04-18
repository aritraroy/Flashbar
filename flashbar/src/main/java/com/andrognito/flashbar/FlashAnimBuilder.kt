package com.andrognito.flashbar

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.support.annotation.InterpolatorRes
import android.view.animation.*
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

    fun duration(millis: Long) = apply {
        require(duration >= 0, { "Duration must not be negative" })
        this.duration = millis
    }

    fun accelerate() = apply {
        this.interpolator = AccelerateInterpolator()
    }

    fun decelerate() = apply {
        this.interpolator = DecelerateInterpolator()
    }

    fun accelerateDecelerate() = apply {
        this.interpolator = AccelerateDecelerateInterpolator()
    }

    fun overshoot() = apply {
        this.interpolator = OvershootInterpolator()
    }

    fun anticipateOvershoot() = apply {
        this.interpolator = AnticipateInterpolator()
    }

    fun interpolator(interpolator: Interpolator) = apply {
        this.interpolator = interpolator
    }

    fun interpolator(@InterpolatorRes id: Int) = apply {
        this.interpolator = AnimationUtils.loadInterpolator(context, id)
    }

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
        translationAnim.propertyName = "translationY"
        translationAnim.target = view

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
}
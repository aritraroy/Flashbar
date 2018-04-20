package com.andrognito.flashbar.anim

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.support.annotation.InterpolatorRes
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator
import com.andrognito.flashbar.Flashbar
import com.andrognito.flashbar.Flashbar.Gravity.BOTTOM
import com.andrognito.flashbar.Flashbar.Gravity.TOP
import com.andrognito.flashbar.anim.FlashAnimBarBuilder.Direction.LEFT
import com.andrognito.flashbar.anim.FlashAnimBarBuilder.Direction.RIGHT
import com.andrognito.flashbar.anim.FlashAnimBarBuilder.Type.ENTER
import com.andrognito.flashbar.anim.FlashAnimBarBuilder.Type.EXIT

class FlashAnimBarBuilder(context: Context) : BaseFlashAnimBuilder(context) {

    private var type: Type? = null
    private var gravity: Flashbar.Gravity? = null
    private var direction: Direction? = null

    override fun withView(view: View) = apply { super.withView(view) }

    override fun duration(millis: Long) = apply { super.duration(millis) }

    override fun accelerate() = apply { super.accelerate() }

    override fun decelerate() = apply { super.decelerate() }

    override fun accelerateDecelerate() = apply { super.accelerateDecelerate() }

    override fun interpolator(interpolator: Interpolator) = apply { super.interpolator(interpolator) }

    override fun interpolator(@InterpolatorRes id: Int) = apply { super.interpolator(id) }

    override fun alpha() = apply { super.alpha() }

    fun slideFromLeft() = apply {
        this.direction = LEFT
    }

    fun slideFromRight() = apply {
        this.direction = RIGHT
    }

    fun overshoot() = apply {
        this.interpolator = OvershootInterpolator()
    }

    fun anticipateOvershoot() = apply {
        this.interpolator = AnticipateInterpolator()
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

    internal fun build(): FlashAnim {
        requireNotNull(view, { "Target view can not be null" })

        val compositeAnim = AnimatorSet()
        val animators = linkedSetOf<Animator>()

        val translationAnim = ObjectAnimator()
        // Slide from left/right animation is not specified, default top/bottom
        // animation is applied
        if (direction == null) {
            translationAnim.propertyName = "translationY"

            when (type!!) {
                ENTER -> when (gravity!!) {
                    TOP -> translationAnim.setFloatValues(-view!!.height.toFloat(), 0f)
                    BOTTOM -> translationAnim.setFloatValues(view!!.height.toFloat(), 0f)
                }
                EXIT -> when (gravity!!) {
                    TOP -> translationAnim.setFloatValues(0f, -view!!.height.toFloat())
                    BOTTOM -> translationAnim.setFloatValues(0f, view!!.height.toFloat())
                }
            }
        } else {
            translationAnim.propertyName = "translationX"

            when (type!!) {
                ENTER -> when (direction!!) {
                    LEFT -> translationAnim.setFloatValues(-view!!.width.toFloat(), 0f)
                    RIGHT -> translationAnim.setFloatValues(view!!.width.toFloat(), 0f)
                }
                EXIT -> when (direction!!) {
                    LEFT -> translationAnim.setFloatValues(0f, -view!!.width.toFloat())
                    RIGHT -> translationAnim.setFloatValues(0f, view!!.width.toFloat())
                }
            }
        }

        translationAnim.target = view
        animators.add(translationAnim)

        if (alpha) {
            val alphaAnim = ObjectAnimator()
            alphaAnim.propertyName = "alpha"
            alphaAnim.target = view

            when (type!!) {
                ENTER -> alphaAnim.setFloatValues(DEFAULT_ALPHA_START, DEFAULT_ALPHA_END)
                EXIT -> alphaAnim.setFloatValues(DEFAULT_ALPHA_END, DEFAULT_ALPHA_START)
            }
            animators.add(alphaAnim)
        }

        compositeAnim.playTogether(animators)
        compositeAnim.duration = duration
        compositeAnim.interpolator = interpolator

        return FlashAnim(compositeAnim)
    }

    enum class Type { ENTER, EXIT }
    enum class Direction { LEFT, RIGHT }
}
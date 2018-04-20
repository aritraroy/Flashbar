package com.andrognito.flashbar.anim

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.content.Context
import android.support.annotation.InterpolatorRes
import android.view.View
import android.view.animation.Interpolator

class FlashAnimIconBuilder(private val context: Context) : BaseFlashAnimBuilder(context) {

    private var pulse: Boolean = false

    override fun withView(view: View) = apply { super.withView(view) }

    override fun duration(millis: Long) = apply { super.duration(millis) }

    override fun accelerate() = apply { super.accelerate() }

    override fun decelerate() = apply { super.decelerate() }

    override fun accelerateDecelerate() = apply {
        super.accelerateDecelerate()
    }

    override fun interpolator(interpolator: Interpolator) = apply { super.interpolator(interpolator) }

    override fun interpolator(@InterpolatorRes id: Int) = apply {
        super.interpolator(id)
    }

    override fun alpha() = apply { super.alpha() }

    fun pulse() = apply {
        this.pulse = true
    }

    internal fun build(): FlashAnim {
        requireNotNull(view, { "Target view can not be null" })

        val compositeAnim = AnimatorSet()
        val animators = linkedSetOf<Animator>()

        if (pulse) {
            val scaleAnim = ObjectAnimator.ofPropertyValuesHolder(view,
                    PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.6f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.6f))

            scaleAnim.repeatCount = INFINITE
            scaleAnim.repeatMode = REVERSE

            animators.add(scaleAnim)
        }

        if (alpha) {
            val alpha = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.6f)

            alpha.repeatCount = INFINITE
            alpha.repeatMode = REVERSE

            animators.add(alpha)
        }

        compositeAnim.playTogether(animators)

        compositeAnim.duration = duration
        compositeAnim.interpolator = interpolator
        return FlashAnim(compositeAnim)
    }
}
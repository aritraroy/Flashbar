package com.andrognito.flashbar

import android.content.Context
import android.support.annotation.AnimRes
import android.support.annotation.InterpolatorRes
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import com.andrognito.flashbar.FlashAnim.Alpha.IN
import com.andrognito.flashbar.FlashAnim.Alpha.OUT
import com.andrognito.flashbar.FlashAnim.Position.BOTTOM
import com.andrognito.flashbar.FlashAnim.Position.TOP
import com.andrognito.flashbar.FlashAnim.Type.ENTER
import com.andrognito.flashbar.FlashAnim.Type.EXIT

abstract class BaseFlashAnimBuilder(private val context: Context) {

    private val DEFAULT_ANIM_DURATION = context.resources
            .getInteger(R.integer.default_animation_duration).toLong()

    private val INTERPOLATOR_LINEAR = AnimationUtils.loadInterpolator(context,
            android.R.anim.linear_interpolator)
    private val INTERPOLATOR_ACCELERATE = AnimationUtils.loadInterpolator(context,
            android.R.anim.accelerate_interpolator)
    private val INTERPOLATOR_DECELERATE = AnimationUtils.loadInterpolator(context,
            android.R.anim.decelerate_interpolator)
    private val INTERPOLATOR_ACCELERATE_DECELERATE = AnimationUtils.loadInterpolator(context,
            android.R.anim.accelerate_decelerate_interpolator)
    private val INTERPOLATOR_OVERSHOOT = AnimationUtils.loadInterpolator(context,
            android.R.anim.overshoot_interpolator)
    private val INTERPOLATOR_ANTICIPATE_OVERSHOOT = AnimationUtils.loadInterpolator(context,
            android.R.anim.anticipate_overshoot_interpolator)

    protected val ANIM_SLIDE_FROM_TOP = AnimationUtils.loadAnimation(context, R.anim.anim_enter_top)
    protected val ANIM_SLIDE_FROM_BOTTOM = AnimationUtils.loadAnimation(context, R.anim.anim_enter_bottom)
    protected val ANIM_SLIDE_TO_TOP = AnimationUtils.loadAnimation(context, R.anim.anim_exit_top)
    protected val ANIM_SLIDE_TO_BOTTOM = AnimationUtils.loadAnimation(context, R.anim.anim_exit_bottom)
    protected val ANIM_ALPHA_IN = AnimationUtils.loadAnimation(context, R.anim.anim_alpha_enter)
    protected val ANIM_ALPHA_OUT = AnimationUtils.loadAnimation(context, R.anim.anim_alpha_exit)

    protected var duration: Long = DEFAULT_ANIM_DURATION
    protected var interpolator: Interpolator = INTERPOLATOR_LINEAR
    protected var animation: Animation? = null
    protected var alpha: FlashAnim.Alpha? = null

    fun accelerate() = apply {
        this.interpolator = INTERPOLATOR_ACCELERATE
    }

    fun decelerate() = apply {
        this.interpolator = INTERPOLATOR_DECELERATE
    }

    fun accelerateDecelerate() = apply {
        this.interpolator = INTERPOLATOR_ACCELERATE_DECELERATE
    }

    fun overshoot() = apply {
        this.interpolator = INTERPOLATOR_OVERSHOOT
    }

    fun anticipateOvershoot() = apply {
        this.interpolator = INTERPOLATOR_ANTICIPATE_OVERSHOOT
    }

    fun interpolator(interpolator: Interpolator) = apply {
        this.interpolator = interpolator
    }

    fun interpolator(@InterpolatorRes id: Int) = apply {
        this.interpolator = AnimationUtils.loadInterpolator(context, id)
    }

    fun animation(animation: Animation) = apply {
        this.animation = animation
    }

    fun animation(@AnimRes id: Int) = apply {
        this.animation = AnimationUtils.loadAnimation(context, id)
    }

    fun alphaIn() = apply { this.alpha = IN }

    fun alphaOut() = apply { this.alpha = OUT }

    fun duration(millis: Long) = apply {
        require(duration >= 0,
                { "Duration must not be negative" })
        this.duration = millis
    }

    abstract fun build(): FlashAnim
}

class FlashAnimIconBuilder(context: Context) : BaseFlashAnimBuilder(context) {

    private val ANIM_PULSE = AnimationUtils.loadAnimation(context, R.anim.anim_pulse)

    fun pulse() = apply { this.animation = ANIM_PULSE }

    override fun build(): FlashAnim {
        val flashAnim = FlashAnim()
        val animationSet = AnimationSet(false)
        animationSet.fillAfter = true
        animationSet.duration = duration
        animationSet.interpolator = interpolator
        animationSet.addAnimation(animation)

        if (alpha != null) {
            when (alpha) {
                IN -> animationSet.addAnimation(ANIM_ALPHA_IN)
                OUT -> animationSet.addAnimation(ANIM_ALPHA_OUT)
            }
        }

        flashAnim.animation = animationSet
        return flashAnim
    }
}

class FlashAnimBarBuilder(context: Context) : BaseFlashAnimBuilder(context) {

    private var type: FlashAnim.Type? = null
    private var position: FlashAnim.Position? = null
    private var alphaEnabled: Boolean = false

    fun enterFrom(position: FlashAnim.Position) = apply {
        require(this.type == null && this.position == null,
                { "Animation position is already initialized" })

        this.type = ENTER
        this.position = position
    }

    fun exitFrom(position: FlashAnim.Position) = apply {
        require(this.type == null && this.position == null,
                { "Animation position is already initialized" })

        this.type = EXIT
        this.position = position
    }

    fun alpha() = apply { this.alphaEnabled = true }

    override fun build(): FlashAnim {
        require(type != null && position != null,
                { "You must specify the animation position" })

        val flashAnim = FlashAnim()
        val animationSet = AnimationSet(false)
        animationSet.fillAfter = true
        animationSet.duration = duration

        animationSet.interpolator = interpolator

        // Only if custom animation is not applied
        if (animation == null) {
            when (type) {
                ENTER -> {
                    when (position) {
                        TOP -> animation = ANIM_SLIDE_FROM_TOP
                        BOTTOM -> animation = ANIM_SLIDE_FROM_BOTTOM
                    }
                }
                EXIT -> {
                    when (position) {
                        TOP -> animation = ANIM_SLIDE_TO_TOP
                        BOTTOM -> animation = ANIM_SLIDE_TO_BOTTOM
                    }
                }
            }
        }
        animationSet.addAnimation(animation)

        if (alphaEnabled) {
            when (type) {
                ENTER -> animationSet.addAnimation(ANIM_ALPHA_IN)
                EXIT -> animationSet.addAnimation(ANIM_ALPHA_OUT)
            }
        } else if (alpha != null) {
            when (alpha) {
                IN -> animationSet.addAnimation(ANIM_ALPHA_IN)
                OUT -> animationSet.addAnimation(ANIM_ALPHA_OUT)
            }
        }

        flashAnim.animation = animationSet
        return flashAnim
    }
}
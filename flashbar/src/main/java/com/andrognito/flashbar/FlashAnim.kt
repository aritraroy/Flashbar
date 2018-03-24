package com.andrognito.flashbar

import android.content.Context
import android.support.annotation.AnimRes
import android.support.annotation.InterpolatorRes
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import com.andrognito.flashbar.FlashAnim.Position.BOTTOM
import com.andrognito.flashbar.FlashAnim.Position.TOP

class FlashAnim {

    private lateinit var animationSet: AnimationSet

    fun getAnimation() = animationSet

    companion object {
        fun with(context: Context): FlashAnimBuilder = FlashAnimBuilder(context)
    }

    class FlashAnimBuilder(private val context: Context) {
        private val DEFAULT_ANIM_DURATION = context.resources
                .getInteger(R.integer.default_animation_duration).toLong()

        private val ANIM_ENTER_TOP = AnimationUtils.loadAnimation(context, R.anim.anim_enter_top)
        private val ANIM_ENTER_BOTTOM = AnimationUtils.loadAnimation(context, R.anim.anim_enter_bottom)
        private val ANIM_EXIT_TOP = AnimationUtils.loadAnimation(context, R.anim.anim_exit_top)
        private val ANIM_EXIT_BOTTOM = AnimationUtils.loadAnimation(context, R.anim.anim_exit_bottom)
        private val ANIM_ALPHA_ENTER = AnimationUtils.loadAnimation(context, R.anim.anim_alpha_enter)
        private val ANIM_ALPHA_EXIT = AnimationUtils.loadAnimation(context, R.anim.anim_alpha_exit)

        private val INTERPOLATOR_ACCELERATE = AnimationUtils.loadInterpolator(context, android.R.anim.accelerate_interpolator)
        private val INTERPOLATOR_DECELERATE = AnimationUtils.loadInterpolator(context, android.R.anim.decelerate_interpolator)
        private val INTERPOLATOR_ACCELERATE_DECELERATE = AnimationUtils.loadInterpolator(context, android.R.anim.accelerate_decelerate_interpolator)
        private val INTERPOLATOR_BOUNCE = AnimationUtils.loadInterpolator(context, android.R.anim.bounce_interpolator)
        private val INTERPOLATOR_OVERSHOOT = AnimationUtils.loadInterpolator(context, android.R.anim.overshoot_interpolator)

        private lateinit var positionAnim: Animation
        private lateinit var alphaAnim: Animation
        private var enterPosition: Position? = null
        private var exitPosition: Position? = null
        private var duration: Long? = null
        private var alpha: Boolean = false

        internal var interpolator: Interpolator? = null

        fun enterFrom(position: Position) = apply {
            this.enterPosition = position
        }

        fun exitFrom(position: Position) = apply {
            this.exitPosition = position
        }

        fun customAnimation(animation: Animation) = apply {
            this.positionAnim = animation
        }

        fun customAnimation(@AnimRes id: Int) = apply {
            this.positionAnim = AnimationUtils.loadAnimation(context, id)
        }

        fun alpha(alpha: Boolean) = apply { this.alpha = alpha }

        fun accelerate() = apply {
            this.interpolator = INTERPOLATOR_ACCELERATE
        }

        fun decelerate() = apply {
            this.interpolator = INTERPOLATOR_DECELERATE
        }

        fun accelerateDecelerate() = apply {
            this.interpolator = INTERPOLATOR_ACCELERATE_DECELERATE
        }

        fun bounce() = apply {
            this.interpolator = INTERPOLATOR_BOUNCE
        }

        fun overshoot() = apply {
            this.interpolator = INTERPOLATOR_OVERSHOOT
        }

        fun interpolator(interpolator: Interpolator) = apply {
            this.interpolator = interpolator
        }

        fun interpolator(@InterpolatorRes id: Int) = apply {
            this.interpolator = AnimationUtils.loadInterpolator(context, id)
        }

        fun duration(duration: Long) = apply {
            this.duration = duration
        }

        fun build(): FlashAnim {
            require((enterPosition != null).xor(exitPosition != null),
                    { "You must specify at least one of the animations but not both" })

            val flashAnim = FlashAnim()
            val animationSet = AnimationSet(false)
            animationSet.fillAfter = true

            if (!::positionAnim.isInitialized) {
                if (enterPosition != null) {
                    when (enterPosition) {
                        TOP -> positionAnim = ANIM_ENTER_TOP
                        BOTTOM -> positionAnim = ANIM_ENTER_BOTTOM
                    }

                    if (alpha) alphaAnim = ANIM_ALPHA_ENTER
                }

                if (exitPosition != null) {
                    when (exitPosition) {
                        TOP -> positionAnim = ANIM_EXIT_TOP
                        BOTTOM -> positionAnim = ANIM_EXIT_BOTTOM
                    }

                    if (alpha) alphaAnim = ANIM_ALPHA_EXIT

                }
            }

            animationSet.addAnimation(positionAnim)
            if (::alphaAnim.isInitialized) {
                animationSet.addAnimation(alphaAnim)
            }

            animationSet.duration = if (duration != null) duration!! else DEFAULT_ANIM_DURATION

            if (interpolator != null) {
                positionAnim.interpolator = interpolator
            }

            flashAnim.animationSet = animationSet
            return flashAnim
        }
    }

    enum class Position {
        TOP,
        BOTTOM,
    }
}
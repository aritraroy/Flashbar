package com.andrognito.flashbar

import android.content.Context
import android.support.annotation.AnimRes
import android.support.annotation.InterpolatorRes
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import com.andrognito.flashbar.FlashAnimFactory.AnimType.*

class FlashAnimFactory {

    companion object {
        fun from(context: Context): FlashAnimRetriever = FlashAnimRetriever(context)
    }

    enum class AnimType {
        ENTER_FROM_TOP,
        ENTER_FROM_TOP_ALPHA,

        ENTER_FROM_BOTTOM,

        EXIT_FROM_TOP,
        EXIT_FROM_TOP_ALPHA,

        EXIT_FROM_BOTTOM
    }

    class FlashAnimRetriever(private val context: Context) {
        fun of(type: AnimType): FlashAnim {
            return when (type) {
                ENTER_FROM_TOP -> FlashAnim(context).anim(R.anim.enter_from_top)
                ENTER_FROM_TOP_ALPHA -> FlashAnim(context).anim(R.anim.enter_from_top_alpha)

                ENTER_FROM_BOTTOM -> FlashAnim(context).anim(R.anim.enter_from_bottom)

                EXIT_FROM_TOP -> FlashAnim(context).anim(R.anim.exit_from_top)
                EXIT_FROM_TOP_ALPHA -> FlashAnim(context).anim(R.anim.exit_from_top_alpha)

                EXIT_FROM_BOTTOM -> FlashAnim(context).anim(R.anim.exit_from_bottom)
            }
        }
    }
}

class FlashAnim(private var context: Context) {

    private lateinit var anim: Animation

    private var duration: Long? = null
    private var interpolator: Interpolator? = null

    fun anim(animation: Animation) = apply { this.anim = animation }

    fun anim(@AnimRes animationId: Int) = apply {
        this.anim = AnimationUtils.loadAnimation(context, animationId)
    }

    fun duration(milliseconds: Long) = apply {
        require(milliseconds > 0) { "Duration can not be negative" }
        this.duration = milliseconds
    }

    fun interpolator(interpolator: Interpolator) = apply {
        this.interpolator = interpolator
    }

    fun interpolator(@InterpolatorRes interpolatorId: Int) = apply {
        this.interpolator = AnimationUtils.loadInterpolator(context, interpolatorId)
    }

    fun build(): Animation {
        if (duration != null) {
            anim.duration = duration!!
        } else {
            anim.duration = context.resources.getInteger(R.integer.default_animation_duration).toLong()
        }

        if (interpolator != null) {
            anim.interpolator = interpolator
        }
        return anim
    }
}
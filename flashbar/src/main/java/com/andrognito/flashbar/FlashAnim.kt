package com.andrognito.flashbar

import android.content.Context
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils

class FlashAnim {

    private lateinit var animationSet: AnimationSet

    fun getAnimation() = animationSet

    companion object {
        fun with(context: Context): FlashAnimBuilder = FlashAnimBuilder(context)
    }

    class FlashAnimBuilder(val context: Context) {
        private val defaultDuration = context.resources
                .getInteger(R.integer.default_animation_duration).toLong()

        private val animEnterTop = AnimationUtils.loadAnimation(context, R.anim.enter_top)
        private val animEnterBottom = AnimationUtils.loadAnimation(context, R.anim.enter_bottom)
        private val animExitTop = AnimationUtils.loadAnimation(context, R.anim.exit_top)
        private val animExitBottom = AnimationUtils.loadAnimation(context, R.anim.exit_bottom)

        private var enterPosition: Position? = null
        private var exitPosition: Position? = null
        private var duration: Long? = null

        fun enter(position: Position) = apply {
            this.enterPosition = position
        }

        fun exit(position: Position) = apply {
            this.exitPosition = position
        }

        fun duration(duration: Long) = apply {
            this.duration = duration
        }

        fun build(): FlashAnim {
            require((enterPosition != null).xor(exitPosition != null),
                    { "You must specify at least one of the animations but not both" })

            val flashAnim = FlashAnim()
            val animationSet = AnimationSet(false)

            if (enterPosition != null) {
                when (enterPosition) {
                    Position.TOP -> animationSet.addAnimation(animEnterTop)
                    Position.BOTTOM -> animationSet.addAnimation(animEnterBottom)
                }
            }

            if (exitPosition != null) {
                when (exitPosition) {
                    Position.TOP -> animationSet.addAnimation(animExitTop)
                    Position.BOTTOM -> animationSet.addAnimation(animExitBottom)
                }
            }

            animationSet.duration = if (duration != null) duration!! else defaultDuration
            flashAnim.animationSet = animationSet
            return flashAnim
        }
    }

    enum class Position {
        TOP,
        BOTTOM,
    }
}
package com.andrognito.flashbar

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context

class FlashAnim(private val translateAnim: ObjectAnimator,
                private val compositeAnim: AnimatorSet) {
    companion object {
        fun with(context: Context) = FlashAnimBuilder(context)
    }

    internal fun start(listener: AnimationListener) {
        translateAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                listener.onStop()
                translateAnim.removeAllListeners()
                translateAnim.removeAllUpdateListeners()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                listener.onStart()
            }
        })

        translateAnim.addUpdateListener {
            listener.onUpdate(it.animatedFraction)
        }

        compositeAnim.start()
    }

    interface AnimationListener {
        fun onStart()
        fun onUpdate(progress: Float)
        fun onStop()
    }
}
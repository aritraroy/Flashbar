package com.andrognito.flashbar

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context

class FlashAnim(private val anim: ObjectAnimator,
                private val animSet: AnimatorSet) {
    companion object {
        fun with(context: Context) = FlashAnimBuilder(context)
    }

    internal fun start(listener: AnimationListener) {
        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                listener.onStop()
                anim.removeAllListeners()
                anim.removeAllUpdateListeners()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                listener.onStart()
            }
        })

        anim.addUpdateListener {
            listener.onUpdate(it.animatedFraction)
        }

        animSet.start()
    }

    interface AnimationListener {
        fun onStart()
        fun onUpdate(progress: Float)
        fun onStop()
    }
}
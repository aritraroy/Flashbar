package com.andrognito.flashbar.anim

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context

class FlashAnim(private val compositeAnim: AnimatorSet) {

    companion object {
        @JvmStatic
        fun with(context: Context) = FlashAnimRetriever(context)
    }

    internal fun start(listener: AnimationListener? = null) {
        if (listener != null) {
            val primaryAnim = compositeAnim.childAnimations[0] as ObjectAnimator

            primaryAnim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    listener.onStop()
                    primaryAnim.removeAllListeners()
                    primaryAnim.removeAllUpdateListeners()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                    listener.onStart()
                }
            })

            primaryAnim.addUpdateListener {
                listener.onUpdate(it.animatedFraction)
            }
        }

        compositeAnim.start()
    }

    interface AnimationListener {
        fun onStart()
        fun onUpdate(progress: Float)
        fun onStop()
    }
}

class FlashAnimRetriever(val context: Context) {
    fun animateBar() = FlashAnimBarBuilder(context)
    fun animateIcon() = FlashAnimIconBuilder(context)
}
package com.andrognito.flashbar

import android.content.Context
import android.view.animation.AnimationSet

class FlashAnim {

    internal lateinit var animation: AnimationSet

    companion object {
        fun with(context: Context) = FlashAnimBuilderRetriever(context)
    }

    enum class Type { ENTER, EXIT, }

    enum class Position { TOP, BOTTOM, }

    enum class Alpha { IN, OUT, }
}

class FlashAnimBuilderRetriever(private val context: Context) {
    fun animateBar() = FlashAnimBarBuilder(context)
    fun animateIcon() = FlashAnimIconBuilder(context)
}
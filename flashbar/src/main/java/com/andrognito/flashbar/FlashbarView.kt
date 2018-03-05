package com.andrognito.flashbar

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.JELLY_BEAN
import android.support.annotation.ColorInt
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.Animation
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.andrognito.flashbar.Flashbar.FlashbarPosition
import com.andrognito.flashbar.Flashbar.FlashbarPosition.BOTTOM
import com.andrognito.flashbar.Flashbar.FlashbarPosition.TOP
import com.andrognito.flashbar.utils.*

/**
 * The actual Flashbar view representation that can consist of the message, button, icon, etc.
 * Its size is adaptive and depends solely on the amount of content present in it. It always matches
 * the width of the screen.
 *
 * It can either be present at the top or at the bottom of the screen. It will always consume touch
 * events and respond as necessary.
 */
class FlashbarView : RelativeLayout {

    private lateinit var flashbarRootView: LinearLayout

    private lateinit var title: TextView
    private lateinit var message: TextView

    constructor(context: Context) : super(context, null, 0) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.flash_bar_view, this)

        flashbarRootView = findViewById(R.id.flash_bar_root)
        title = flashbarRootView.findViewById(R.id.title)
        message = flashbarRootView.findViewById(R.id.message)
    }

    internal fun adjustWitPositionAndOrientation(activity: Activity, flashbarPosition: FlashbarPosition) {
        val flashbarViewLp = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val statusBarHeight = activity.getStatusBarHeightInPx()

        when (flashbarPosition) {
            TOP -> {
                val flashbarViewContent = findViewById<View>(R.id.flash_bar_content)
                val flashbarViewContentLp = flashbarViewContent.layoutParams as LinearLayout.LayoutParams

                flashbarViewContentLp.topMargin = statusBarHeight
                flashbarViewContent.layoutParams = flashbarViewContentLp
                flashbarViewLp.addRule(RelativeLayout.ALIGN_PARENT_TOP)
            }
            BOTTOM -> {
                flashbarViewLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            }
        }

        layoutParams = flashbarViewLp
    }

    internal fun setBarBackground(drawable: Drawable?) {
        if (drawable == null) return

        if (SDK_INT >= JELLY_BEAN) {
            this.flashbarRootView.background = drawable
        } else {
            this.flashbarRootView.setBackgroundDrawable(drawable)
        }
    }

    internal fun setBarBackgroundColor(@ColorInt color: Int?) {
        if (color == null) return
        this.flashbarRootView.setBackgroundColor(color)
    }

    internal fun setTitle(title: String?) {
        if (TextUtils.isEmpty(title)) return

        this.title.text = title
        this.title.visibility = VISIBLE
    }

    internal fun setTitleTypeface(typeface: Typeface?) {
        if (typeface == null) return
        title.typeface = typeface
    }

    fun setTitleSizeInPx(size: Float?) {
        if (size == null) return
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    fun setTitleSizeInSp(size: Float?) {
        if (size == null) return
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setMessage(message: String?) {
        if (TextUtils.isEmpty(message)) return

        this.message.text = message
        this.message.visibility = VISIBLE
    }

    internal fun setMessageTypeface(typeface: Typeface?) {
        if (typeface == null) return
        message.typeface = typeface
    }

    fun setMessageSizeInPx(size: Float?) {
        if (size == null) return
        message.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    fun setMessageSizeInSp(size: Float?) {
        if (size == null) return
        message.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }
}

/**
 * Container view matching the height and width of the parent to hold a FlashbarView.
 * It will occupy the entire screens size but will be completely transparent. The
 * FlashbarView inside is the only visible component in it.
 */
class FlashbarContainerView(context: Context) : RelativeLayout(context) {

    private lateinit var flashbarView: FlashbarView

    private lateinit var enterAnimation: Animation
    private lateinit var exitAnimation: Animation

    private var isBarShowing = false
    private var isBarShown = false
    private var isBarDismissing = false

    private val enterAnimationListener = object : Animation.AnimationListener {

        override fun onAnimationStart(animation: Animation) {
            isBarShowing = true
        }

        override fun onAnimationEnd(animation: Animation) {
            isBarShowing = false
            isBarShown = true
        }

        override fun onAnimationRepeat(animation: Animation) {
            // NO-OP
        }
    }

    private val exitAnimationListener = object : Animation.AnimationListener {

        override fun onAnimationStart(animation: Animation?) {
            isBarDismissing = true
        }

        override fun onAnimationEnd(animation: Animation?) {
            isBarDismissing = false
            isBarShown = false

            // Removing container after animation end
            post { (parent as? ViewGroup)?.removeView(this@FlashbarContainerView) }
        }

        override fun onAnimationRepeat(animation: Animation?) {
            // NO-OP
        }
    }

    internal fun add(flashbarView: FlashbarView) {
        this.flashbarView = flashbarView
        addView(flashbarView)
    }

    internal fun adjustPositionAndOrientation(activity: Activity) {
        val flashbarContainerViewLp = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

        val navigationBarPosition = activity.getNavigationBarPosition()
        val navigationBarSize = activity.getNavigationBarSizeInPx()

        when (navigationBarPosition) {
            NavigationBarPosition.LEFT -> flashbarContainerViewLp.leftMargin = navigationBarSize
            NavigationBarPosition.RIGHT -> flashbarContainerViewLp.rightMargin = navigationBarSize
            NavigationBarPosition.BOTTOM -> flashbarContainerViewLp.bottomMargin = navigationBarSize
        }

        layoutParams = flashbarContainerViewLp
    }

    internal fun show(activity: Activity) {
        if (isBarShowing || isBarShown) {
            return
        }

        val activityRootView = activity.getRootView()
        activityRootView?.addView(this)

        enterAnimation.setAnimationListener(enterAnimationListener)
        flashbarView.startAnimation(enterAnimation)
    }

    internal fun dismiss() {
        if (isBarDismissing || isBarShowing || !isBarShown) {
            return
        }

        exitAnimation.setAnimationListener(exitAnimationListener)
        flashbarView.startAnimation(exitAnimation)
    }

    internal fun isBarShowing() = isBarShowing

    internal fun isBarShown() = isBarShown

    internal fun setTitle(title: String?) {
        flashbarView.setTitle(title)
    }

    fun setTitleSizeInPx(size: Float?) {
        flashbarView.setTitleSizeInPx(size)
    }

    fun setTitleSizeInSp(size: Float?) {
        flashbarView.setTitleSizeInSp(size)
    }

    internal fun setTitleTypeface(typeface: Typeface?) {
        flashbarView.setTitleTypeface(typeface)
    }

    internal fun setMessage(message: String?) {
        flashbarView.setMessage(message)
    }

    internal fun setMessageTypeface(typeface: Typeface?) {
        flashbarView.setMessageTypeface(typeface)
    }

    fun setMessageSizeInPx(size: Float?) {
        flashbarView.setMessageSizeInPx(size)
    }

    fun setMessageSizeInSp(size: Float?) {
        flashbarView.setMessageSizeInSp(size)
    }

    internal fun setEnterAnimation(animation: Animation) {
        enterAnimation = animation
    }

    internal fun setExitAnimation(animation: Animation) {
        exitAnimation = animation
    }

    internal fun setBarBackgroundColor(@ColorInt color: Int?) {
        flashbarView.setBarBackgroundColor(color)
    }

    internal fun setBarBackgroundDrawable(drawable: Drawable?) {
        flashbarView.setBarBackground(drawable)
    }
}

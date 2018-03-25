package com.andrognito.flashbar

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.support.annotation.ColorInt
import android.text.Spanned
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM
import android.widget.RelativeLayout.ALIGN_PARENT_TOP
import com.andrognito.flashbar.Flashbar.FlashbarPosition
import com.andrognito.flashbar.Flashbar.FlashbarPosition.BOTTOM
import com.andrognito.flashbar.Flashbar.FlashbarPosition.TOP
import com.andrognito.flashbar.Flashbar.ProgressPosition.LEFT
import com.andrognito.flashbar.Flashbar.ProgressPosition.RIGHT
import com.andrognito.flashbar.util.convertDpToPx
import com.andrognito.flashbar.util.getStatusBarHeightInPx
import com.andrognito.flashbar.view.ShadowView
import com.andrognito.flashbar.view.SwipeDismissTouchListener
import com.andrognito.flashbar.view.SwipeDismissTouchListener.DismissCallbacks
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

private const val DEFAULT_ELEVATION = 4

/**
 * The actual Flashbar view representation that can consist of the title, message, button, icon, etc.
 * Its size is adaptive and depends solely on the amount of content present in it. It always matches
 * the width of the screen.
 *
 * It can either be present at the top or at the bottom of the screen. It will always consume touch
 * events and respond as necessary.
 */
internal class FlashbarView(context: Context) : LinearLayout(context) {

    private val TOP_COMPENSATION_MARGIN = resources.getDimension(R.dimen.fb_top_compensation_margin).toInt()
    private val BOTTOM_COMPENSATION_MARGIN = resources.getDimension(R.dimen.fb_bottom_compensation_margin).toInt()

    private lateinit var flashbarRootView: LinearLayout
    private lateinit var parentFlashbarContainerView: FlashbarContainerView

    private lateinit var position: FlashbarPosition

    private lateinit var titleView: TextView
    private lateinit var messageView: TextView
    private lateinit var iconView: ImageView
    private lateinit var buttonView: Button
    private lateinit var leftProgressView: MaterialProgressBar
    private lateinit var rightProgressView: MaterialProgressBar

    private var isMarginCompensationApplied: Boolean = false

    internal fun init(
            position: FlashbarPosition,
            castShadow: Boolean,
            shadowStrength: Int?) {
        this.position = position
        this.orientation = VERTICAL

        // If the bar appears with the bottom, then the shadow needs to added to the top of it.
        // Thus, before the inflation of the bar
        if (castShadow && position == BOTTOM) {
            castShadow(ShadowView.ShadowType.TOP, shadowStrength ?: DEFAULT_ELEVATION)
        }

        inflate(context, R.layout.flash_bar_view, this)

        // If the bar appears with the top, then the shadow needs to added to the bottom of it.
        // Thus, after the inflation of the bar
        if (castShadow && position == TOP) {
            castShadow(ShadowView.ShadowType.BOTTOM, shadowStrength ?: DEFAULT_ELEVATION)
        }

        flashbarRootView = findViewById(R.id.fb_root)

        with(flashbarRootView) {
            titleView = findViewById(R.id.fb_title)
            messageView = findViewById(R.id.fb_message)
            iconView = findViewById(R.id.fb_icon)
            buttonView = findViewById(R.id.fb_action)
            leftProgressView = findViewById(R.id.fb_progress_left)
            rightProgressView = findViewById(R.id.fb_progress_right)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (!isMarginCompensationApplied) {
            isMarginCompensationApplied = true

            val params = layoutParams as ViewGroup.MarginLayoutParams
            when (position) {
                TOP -> params.topMargin = -TOP_COMPENSATION_MARGIN
                BOTTOM -> params.bottomMargin = -BOTTOM_COMPENSATION_MARGIN
            }
            requestLayout()
        }
    }

    internal fun adjustWitPositionAndOrientation(activity: Activity,
                                                 flashbarPosition: FlashbarPosition) {
        val flashbarViewLp = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val statusBarHeight = activity.getStatusBarHeightInPx()

        val flashbarViewContent = findViewById<LinearLayout>(R.id.fb_content)
        val flashbarViewContentLp = flashbarViewContent.layoutParams as LinearLayout.LayoutParams

        when (flashbarPosition) {
            TOP -> {
                flashbarViewContentLp.topMargin = statusBarHeight
                        .plus(TOP_COMPENSATION_MARGIN.times(1.5f).toInt())
                flashbarViewLp.addRule(ALIGN_PARENT_TOP)
            }
            BOTTOM -> {
                flashbarViewContentLp.bottomMargin = BOTTOM_COMPENSATION_MARGIN.times(1.5f).toInt()
                flashbarViewLp.addRule(ALIGN_PARENT_BOTTOM)
            }
        }
        flashbarViewContent.layoutParams = flashbarViewContentLp
        layoutParams = flashbarViewLp
    }

    internal fun addParent(flashbarContainerView: FlashbarContainerView) {
        this.parentFlashbarContainerView = flashbarContainerView
    }

    internal fun setBarBackgroundDrawable(drawable: Drawable?) {
        if (drawable == null) return

        if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.flashbarRootView.background = drawable
        } else {
            this.flashbarRootView.setBackgroundDrawable(drawable)
        }
    }

    internal fun setBarBackgroundColor(@ColorInt color: Int?) {
        if (color == null) return
        this.flashbarRootView.setBackgroundColor(color)
    }

    internal fun setBarTapListener(listener: Flashbar.OnBarTapListener?) {
        if (listener == null) return

        this.flashbarRootView.setOnClickListener {
            listener.onBarTapped(parentFlashbarContainerView.parentFlashbar)
        }
    }

    internal fun setTitle(title: String?) {
        if (TextUtils.isEmpty(title)) return

        this.titleView.text = title
        this.titleView.visibility = VISIBLE
    }

    internal fun setTitleSpanned(title: Spanned?) {
        if (title == null) return

        this.titleView.text = title
        this.titleView.visibility = VISIBLE
    }

    internal fun setTitleTypeface(typeface: Typeface?) {
        if (typeface == null) return
        titleView.typeface = typeface
    }

    internal fun setTitleSizeInPx(size: Float?) {
        if (size == null) return
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setTitleSizeInSp(size: Float?) {
        if (size == null) return
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setTitleColor(color: Int?) {
        if (color == null) return
        titleView.setTextColor(color)
    }

    internal fun setTitleAppearance(titleAppearance: Int?) {
        if (titleAppearance == null) return

        if (SDK_INT >= M) {
            this.titleView.setTextAppearance(titleAppearance)
        } else {
            this.titleView.setTextAppearance(titleView.context, titleAppearance)
        }
    }

    internal fun setMessage(message: String?) {
        if (TextUtils.isEmpty(message)) return

        this.messageView.text = message
        this.messageView.visibility = VISIBLE
    }

    internal fun setMessageSpanned(message: Spanned?) {
        if (message == null) return

        this.messageView.text = message
        this.messageView.visibility = VISIBLE
    }

    internal fun setMessageTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.messageView.typeface = typeface
    }

    internal fun setMessageSizeInPx(size: Float?) {
        if (size == null) return
        this.messageView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setMessageSizeInSp(size: Float?) {
        if (size == null) return
        this.messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setMessageColor(color: Int?) {
        if (color == null) return
        this.messageView.setTextColor(color)
    }

    internal fun setMessageAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.messageView.setTextAppearance(messageAppearance)
        } else {
            this.messageView.setTextAppearance(messageView.context, messageAppearance)
        }
    }

    internal fun setActionText(text: String?) {
        if (TextUtils.isEmpty(text)) return

        this.buttonView.text = text
        this.buttonView.visibility = VISIBLE
    }

    internal fun setActionTextSpanned(text: Spanned?) {
        if (text == null) return

        this.buttonView.text = text
        this.buttonView.visibility = VISIBLE
    }

    internal fun setActionTextTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.buttonView.typeface = typeface
    }

    internal fun setActionTextSizeInPx(size: Float?) {
        if (size == null) return
        this.buttonView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setActionTextSizeInSp(size: Float?) {
        if (size == null) return
        this.buttonView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setActionTextColor(color: Int?) {
        if (color == null) return
        this.buttonView.setTextColor(color)
    }

    internal fun setActionTextAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.buttonView.setTextAppearance(messageAppearance)
        } else {
            this.buttonView.setTextAppearance(buttonView.context, messageAppearance)
        }
    }

    internal fun setActionTapListener(listener: Flashbar.OnActionTapListener?) {
        if (listener == null) return

        this.buttonView.setOnClickListener {
            listener.onActionTapped(parentFlashbarContainerView.parentFlashbar)
        }
    }

    internal fun showIcon(showIcon: Boolean) {
        this.iconView.visibility = if (showIcon) VISIBLE else GONE
    }

    internal fun setIconDrawable(icon: Drawable?) {
        if (icon == null) return
        this.iconView.setImageDrawable(icon)
    }

    internal fun setIconBitmap(bitmap: Bitmap?) {
        if (bitmap == null) return
        this.iconView.setImageBitmap(bitmap)
    }

    internal fun setIconColorFilter(colorFilter: Int?, filterMode: PorterDuff.Mode?) {
        if (colorFilter == null) return
        if (filterMode == null) {
            this.iconView.setColorFilter(colorFilter)
        } else {
            this.iconView.setColorFilter(colorFilter, filterMode)
        }
    }

    internal fun startIconAnimation(anim: FlashAnim?) {
        if (anim == null) return
        iconView.startAnimation(anim.animation)
    }

    internal fun stopIconAnimation() {
        iconView.clearAnimation()
    }

    internal fun enableSwipeToDismiss(enable: Boolean, callbacks: DismissCallbacks) {
        if (enable) {
            flashbarRootView.setOnTouchListener(SwipeDismissTouchListener(this, callbacks))
        }
    }

    internal fun setProgressPosition(position: Flashbar.ProgressPosition?) {
        if (position == null) return
        when (position) {
            LEFT -> {
                leftProgressView.visibility = View.VISIBLE
                rightProgressView.visibility = View.GONE
            }
            RIGHT -> {
                leftProgressView.visibility = View.GONE
                rightProgressView.visibility = View.VISIBLE
            }
        }
    }

    private fun castShadow(shadowType: ShadowView.ShadowType, strength: Int) {
        val params = RelativeLayout.LayoutParams(MATCH_PARENT, context.convertDpToPx(strength))
        val shadow = ShadowView(context)
        shadow.applyShadow(shadowType)
        addView(shadow, params)
    }
}
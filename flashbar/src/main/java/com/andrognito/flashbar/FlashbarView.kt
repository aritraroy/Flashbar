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
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM
import android.widget.RelativeLayout.ALIGN_PARENT_TOP
import com.andrognito.flashbar.Flashbar.Gravity
import com.andrognito.flashbar.Flashbar.Gravity.BOTTOM
import com.andrognito.flashbar.Flashbar.Gravity.TOP
import com.andrognito.flashbar.Flashbar.ProgressPosition.LEFT
import com.andrognito.flashbar.Flashbar.ProgressPosition.RIGHT
import com.andrognito.flashbar.SwipeDismissTouchListener.DismissCallbacks
import com.andrognito.flashbar.anim.FlashAnimIconBuilder
import com.andrognito.flashbar.util.convertDpToPx
import com.andrognito.flashbar.util.getStatusBarHeightInPx
import com.andrognito.flashbar.view.ShadowView
import kotlinx.android.synthetic.main.flash_bar_view.view.*

/**
 * The actual Flashbar withView representation that can consist of the title, message, button, icon, etc.
 * Its size is adaptive and depends solely on the amount of content present in it. It always matches
 * the width of the screen.
 *
 * It can either be present at the top or at the bottom of the screen. It will always consume touch
 * events and respond as necessary.
 */
internal class FlashbarView(context: Context) : LinearLayout(context) {

    private val TOP_COMPENSATION_MARGIN = resources.getDimension(R.dimen.fb_top_compensation_margin).toInt()
    private val BOTTOM_COMPENSATION_MARGIN = resources.getDimension(R.dimen.fb_bottom_compensation_margin).toInt()

    private lateinit var parentFlashbarContainer: FlashbarContainerView
    private lateinit var gravity: Gravity

    private var isMarginCompensationApplied: Boolean = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (!isMarginCompensationApplied) {
            isMarginCompensationApplied = true

            val params = layoutParams as ViewGroup.MarginLayoutParams
            when (gravity) {
                TOP -> params.topMargin = -TOP_COMPENSATION_MARGIN
                BOTTOM -> params.bottomMargin = -BOTTOM_COMPENSATION_MARGIN
            }
            requestLayout()
        }
    }

    internal fun init(
            gravity: Gravity,
            castShadow: Boolean,
            shadowStrength: Int) {
        this.gravity = gravity
        this.orientation = VERTICAL

        // If the bar appears with the bottom, then the shadow needs to added to the top of it,
        // Thus, before the inflation of the bar
        if (castShadow && gravity == BOTTOM) {
            castShadow(ShadowView.ShadowType.TOP, shadowStrength)
        }

        inflate(context, R.layout.flash_bar_view, this)

        // If the bar appears with the top, then the shadow needs to added to the bottom of it,
        // Thus, after the inflation of the bar
        if (castShadow && gravity == TOP) {
            castShadow(ShadowView.ShadowType.BOTTOM, shadowStrength)
        }
    }

    internal fun adjustWitPositionAndOrientation(activity: Activity,
                                                 gravity: Gravity) {
        val flashbarViewLp = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val statusBarHeight = activity.getStatusBarHeightInPx()

        val flashbarViewContentLp = fbContent.layoutParams as LinearLayout.LayoutParams

        when (gravity) {
            TOP -> {
                flashbarViewContentLp.topMargin = statusBarHeight.plus(TOP_COMPENSATION_MARGIN / 2)
                flashbarViewLp.addRule(ALIGN_PARENT_TOP)
            }
            BOTTOM -> {
                flashbarViewContentLp.bottomMargin = BOTTOM_COMPENSATION_MARGIN
                flashbarViewLp.addRule(ALIGN_PARENT_BOTTOM)
            }
        }
        fbContent.layoutParams = flashbarViewContentLp
        layoutParams = flashbarViewLp
    }

    internal fun addParent(flashbarContainerView: FlashbarContainerView) {
        this.parentFlashbarContainer = flashbarContainerView
    }

    internal fun setBarBackgroundDrawable(drawable: Drawable?) {
        if (drawable == null) return

        if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.fbRoot.background = drawable
        } else {
            this.fbRoot.setBackgroundDrawable(drawable)
        }
    }

    internal fun setBarBackgroundColor(@ColorInt color: Int?) {
        if (color == null) return
        this.fbRoot.setBackgroundColor(color)
    }

    internal fun setBarTapListener(listener: Flashbar.OnTapListener?) {
        if (listener == null) return

        this.fbRoot.setOnClickListener {
            listener.onTap(parentFlashbarContainer.parentFlashbar)
        }
    }

    internal fun setTitle(title: String?) {
        if (TextUtils.isEmpty(title)) return

        this.fbTitle.text = title
        this.fbTitle.visibility = VISIBLE
    }

    internal fun setTitleSpanned(title: Spanned?) {
        if (title == null) return

        this.fbTitle.text = title
        this.fbTitle.visibility = VISIBLE
    }

    internal fun setTitleTypeface(typeface: Typeface?) {
        if (typeface == null) return
        fbTitle.typeface = typeface
    }

    internal fun setTitleSizeInPx(size: Float?) {
        if (size == null) return
        fbTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setTitleSizeInSp(size: Float?) {
        if (size == null) return
        fbTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setTitleColor(color: Int?) {
        if (color == null) return
        fbTitle.setTextColor(color)
    }

    internal fun setTitleAppearance(titleAppearance: Int?) {
        if (titleAppearance == null) return

        if (SDK_INT >= M) {
            this.fbTitle.setTextAppearance(titleAppearance)
        } else {
            this.fbTitle.setTextAppearance(fbTitle.context, titleAppearance)
        }
    }

    internal fun setMessage(message: String?) {
        if (TextUtils.isEmpty(message)) return

        this.fbMessage.text = message
        this.fbMessage.visibility = VISIBLE
    }

    internal fun setMessageSpanned(message: Spanned?) {
        if (message == null) return

        this.fbMessage.text = message
        this.fbMessage.visibility = VISIBLE
    }

    internal fun setMessageTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.fbMessage.typeface = typeface
    }

    internal fun setMessageSizeInPx(size: Float?) {
        if (size == null) return
        this.fbMessage.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setMessageSizeInSp(size: Float?) {
        if (size == null) return
        this.fbMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setMessageColor(color: Int?) {
        if (color == null) return
        this.fbMessage.setTextColor(color)
    }

    internal fun setMessageAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.fbMessage.setTextAppearance(messageAppearance)
        } else {
            this.fbMessage.setTextAppearance(fbMessage.context, messageAppearance)
        }
    }

    internal fun setPrimaryActionText(text: String?) {
        if (TextUtils.isEmpty(text)) return

        this.fbPrimaryAction.text = text
        this.fbPrimaryAction.visibility = VISIBLE
    }

    internal fun setPrimaryActionTextSpanned(text: Spanned?) {
        if (text == null) return

        this.fbPrimaryAction.text = text
        this.fbPrimaryAction.visibility = VISIBLE
    }

    internal fun setPrimaryActionTextTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.fbPrimaryAction.typeface = typeface
    }

    internal fun setPrimaryActionTextSizeInPx(size: Float?) {
        if (size == null) return
        this.fbPrimaryAction.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setPrimaryActionTextSizeInSp(size: Float?) {
        if (size == null) return
        this.fbPrimaryAction.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setPrimaryActionTextColor(color: Int?) {
        if (color == null) return
        this.fbPrimaryAction.setTextColor(color)
    }

    internal fun setPrimaryActionTextAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.fbPrimaryAction.setTextAppearance(messageAppearance)
        } else {
            this.fbPrimaryAction.setTextAppearance(fbPrimaryAction.context, messageAppearance)
        }
    }

    internal fun setPrimaryActionTapListener(listener: Flashbar.OnActionTapListener?) {
        if (listener == null) return

        this.fbPrimaryAction.setOnClickListener {
            listener.onActionTapped(parentFlashbarContainer.parentFlashbar)
        }
    }

    internal fun setPositiveActionText(text: String?) {
        if (TextUtils.isEmpty(text)) return

        this.fbSecondaryActionContainer.visibility = VISIBLE
        this.fbPositiveAction.text = text
        this.fbPositiveAction.visibility = VISIBLE
    }

    internal fun setPositiveActionTextSpanned(text: Spanned?) {
        if (text == null) return

        this.fbSecondaryActionContainer.visibility = VISIBLE
        this.fbPositiveAction.text = text
        this.fbPositiveAction.visibility = VISIBLE
    }

    internal fun setPositiveActionTextTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.fbPositiveAction.typeface = typeface
    }

    internal fun setPositiveActionTextSizeInPx(size: Float?) {
        if (size == null) return
        this.fbPositiveAction.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setPositiveActionTextSizeInSp(size: Float?) {
        if (size == null) return
        this.fbPositiveAction.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setPositiveActionTextColor(color: Int?) {
        if (color == null) return
        this.fbPositiveAction.setTextColor(color)
    }

    internal fun setPositiveActionTextAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.fbPositiveAction.setTextAppearance(messageAppearance)
        } else {
            this.fbPositiveAction.setTextAppearance(fbPrimaryAction.context, messageAppearance)
        }
    }

    internal fun setPositiveActionTapListener(listener: Flashbar.OnActionTapListener?) {
        if (listener == null) return

        this.fbPositiveAction.setOnClickListener {
            listener.onActionTapped(parentFlashbarContainer.parentFlashbar)
        }
    }

    internal fun setNegativeActionText(text: String?) {
        if (TextUtils.isEmpty(text)) return

        this.fbSecondaryActionContainer.visibility = VISIBLE
        this.fbNegativeAction.text = text
        this.fbNegativeAction.visibility = VISIBLE
    }

    internal fun setNegativeActionTextSpanned(text: Spanned?) {
        if (text == null) return

        this.fbSecondaryActionContainer.visibility = VISIBLE
        this.fbNegativeAction.text = text
        this.fbNegativeAction.visibility = VISIBLE
    }

    internal fun setNegativeActionTextTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.fbNegativeAction.typeface = typeface
    }

    internal fun setNegativeActionTextSizeInPx(size: Float?) {
        if (size == null) return
        this.fbNegativeAction.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setNegativeActionTextSizeInSp(size: Float?) {
        if (size == null) return
        this.fbNegativeAction.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setNegativeActionTextColor(color: Int?) {
        if (color == null) return
        this.fbNegativeAction.setTextColor(color)
    }

    internal fun setNegativeActionTextAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.fbNegativeAction.setTextAppearance(messageAppearance)
        } else {
            this.fbNegativeAction.setTextAppearance(fbPrimaryAction.context, messageAppearance)
        }
    }

    internal fun setNegativeActionTapListener(listener: Flashbar.OnActionTapListener?) {
        if (listener == null) return

        this.fbNegativeAction.setOnClickListener {
            listener.onActionTapped(parentFlashbarContainer.parentFlashbar)
        }
    }

    internal fun showIcon(showIcon: Boolean) {
        this.fbIcon.visibility = if (showIcon) VISIBLE else GONE
    }

    internal fun showIconScale(scale: Float, scaleType: ImageView.ScaleType?) {
        this.fbIcon.scaleX = scale
        this.fbIcon.scaleY = scale
        this.fbIcon.scaleType = scaleType
    }

    internal fun setIconDrawable(icon: Drawable?) {
        if (icon == null) return
        this.fbIcon.setImageDrawable(icon)
    }

    internal fun setIconBitmap(bitmap: Bitmap?) {
        if (bitmap == null) return
        this.fbIcon.setImageBitmap(bitmap)
    }

    internal fun setIconColorFilter(colorFilter: Int?, filterMode: PorterDuff.Mode?) {
        if (colorFilter == null) return
        if (filterMode == null) {
            this.fbIcon.setColorFilter(colorFilter)
        } else {
            this.fbIcon.setColorFilter(colorFilter, filterMode)
        }
    }

    internal fun startIconAnimation(animator: FlashAnimIconBuilder?) {
        animator?.withView(fbIcon)?.build()?.start()
    }

    internal fun stopIconAnimation() {
        fbIcon.clearAnimation()
    }

    internal fun enableSwipeToDismiss(enable: Boolean, callbacks: DismissCallbacks) {
        if (enable) {
            fbRoot.setOnTouchListener(SwipeDismissTouchListener(this, callbacks))
        }
    }

    internal fun setProgressPosition(position: Flashbar.ProgressPosition?) {
        if (position == null) return
        when (position) {
            LEFT -> {
                fbLeftProgress.visibility = VISIBLE
                fbRightProgress.visibility = GONE
            }
            RIGHT -> {
                fbLeftProgress.visibility = GONE
                fbRightProgress.visibility = VISIBLE
            }
        }
    }

    internal fun setProgressTint(progressTint: Int?,
                                 position: Flashbar.ProgressPosition?) {
        if (position == null || progressTint == null) return

        val progressBar = when (position) {
            LEFT -> fbLeftProgress
            RIGHT -> fbRightProgress
        }

        progressBar.setBarColor(progressTint)
    }

    private fun castShadow(shadowType: ShadowView.ShadowType, strength: Int) {
        val params = RelativeLayout.LayoutParams(MATCH_PARENT, context.convertDpToPx(strength))
        val shadow = ShadowView(context)
        shadow.applyShadow(shadowType)
        addView(shadow, params)
    }
}
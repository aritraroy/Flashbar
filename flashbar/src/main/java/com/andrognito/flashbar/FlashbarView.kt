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
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.*
import com.andrognito.flashbar.Flashbar.FlashbarPosition
import com.andrognito.flashbar.Flashbar.FlashbarPosition.BOTTOM
import com.andrognito.flashbar.Flashbar.FlashbarPosition.TOP
import com.andrognito.flashbar.listener.OnActionTapListener
import com.andrognito.flashbar.listener.OnBarTapListener
import com.andrognito.flashbar.util.getStatusBarHeightInPx

/**
 * The actual Flashbar view representation that can consist of the message, button, icon, etc.
 * Its size is adaptive and depends solely on the amount of content present in it. It always matches
 * the width of the screen.
 *
 * It can either be present at the top or at the bottom of the screen. It will always consume touch
 * events and respond as necessary.
 */
internal class FlashbarView : RelativeLayout {

    private lateinit var flashbarRootView: LinearLayout
    private lateinit var parentFlashbarContainerView: FlashbarContainerView

    private lateinit var title: TextView
    private lateinit var message: TextView
    private lateinit var icon: ImageView
    private lateinit var button: Button

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

        flashbarRootView = findViewById(R.id.fb_root)

        with(flashbarRootView) {
            title = findViewById(R.id.fb_title)
            message = findViewById(R.id.fb_message)
            icon = findViewById(R.id.fb_icon)
            button = findViewById(R.id.fb_action)
        }
    }

    internal fun adjustWitPositionAndOrientation(activity: Activity,
                                                 flashbarPosition: FlashbarPosition) {
        val flashbarViewLp = RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        val statusBarHeight = activity.getStatusBarHeightInPx()

        when (flashbarPosition) {
            TOP -> {
                val flashbarViewContent = findViewById<LinearLayout>(R.id.fb_content)
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

    internal fun setBarTapListener(listener: OnBarTapListener?) {
        if (listener == null) return

        this.flashbarRootView.setOnClickListener {
            listener.onBarTapped(parentFlashbarContainerView.parentFlashbar)
        }
    }

    internal fun setTitle(title: String?) {
        if (TextUtils.isEmpty(title)) return

        this.title.text = title
        this.title.visibility = VISIBLE
    }

    internal fun setTitleSpanned(title: Spanned?) {
        if (title == null) return

        this.title.text = title
        this.title.visibility = VISIBLE
    }

    internal fun setTitleTypeface(typeface: Typeface?) {
        if (typeface == null) return
        title.typeface = typeface
    }

    internal fun setTitleSizeInPx(size: Float?) {
        if (size == null) return
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setTitleSizeInSp(size: Float?) {
        if (size == null) return
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setTitleColor(color: Int?) {
        if (color == null) return
        title.setTextColor(color)
    }

    internal fun setTitleAppearance(titleAppearance: Int?) {
        if (titleAppearance == null) return

        if (SDK_INT >= M) {
            this.title.setTextAppearance(titleAppearance)
        } else {
            this.title.setTextAppearance(title.context, titleAppearance)
        }
    }

    internal fun setMessage(message: String?) {
        if (TextUtils.isEmpty(message)) return

        this.message.text = message
        this.message.visibility = VISIBLE
    }

    internal fun setMessageSpanned(message: Spanned?) {
        if (message == null) return

        this.message.text = message
        this.message.visibility = VISIBLE
    }

    internal fun setMessageTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.message.typeface = typeface
    }

    internal fun setMessageSizeInPx(size: Float?) {
        if (size == null) return
        this.message.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setMessageSizeInSp(size: Float?) {
        if (size == null) return
        this.message.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setMessageColor(color: Int?) {
        if (color == null) return
        this.message.setTextColor(color)
    }

    internal fun setMessageAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.message.setTextAppearance(messageAppearance)
        } else {
            this.message.setTextAppearance(message.context, messageAppearance)
        }
    }

    internal fun setActionText(text: String?) {
        if (TextUtils.isEmpty(text)) return

        this.button.text = text
        this.button.visibility = VISIBLE
    }

    internal fun setActionTextSpanned(text: Spanned?) {
        if (text == null) return

        this.button.text = text
        this.button.visibility = VISIBLE
    }

    internal fun setActionTextTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.button.typeface = typeface
    }

    internal fun setActionTextSizeInPx(size: Float?) {
        if (size == null) return
        this.button.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setActionTextSizeInSp(size: Float?) {
        if (size == null) return
        this.button.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setActionTextColor(color: Int?) {
        if (color == null) return
        this.button.setTextColor(color)
    }

    internal fun setActionTextAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.button.setTextAppearance(messageAppearance)
        } else {
            this.button.setTextAppearance(button.context, messageAppearance)
        }
    }

    internal fun setActionTapListener(listener: OnActionTapListener?) {
        if (listener == null) return

        this.button.setOnClickListener {
            listener.onActionTapped(parentFlashbarContainerView.parentFlashbar)
        }
    }

    internal fun showIcon(showIcon: Boolean) {
        this.icon.visibility = if (showIcon) VISIBLE else GONE
    }

    internal fun setIconDrawable(icon: Drawable?) {
        if (icon == null) return
        this.icon.setImageDrawable(icon)
    }

    internal fun setIconBitmap(bitmap: Bitmap?) {
        if (bitmap == null) return
        this.icon.setImageBitmap(bitmap)
    }

    internal fun setIconColorFilter(colorFilter: Int?, filterMode: PorterDuff.Mode?) {
        if (colorFilter == null) return
        if (filterMode == null) {
            this.icon.setColorFilter(colorFilter)
        } else {
            this.icon.setColorFilter(colorFilter, filterMode)
        }
    }
}
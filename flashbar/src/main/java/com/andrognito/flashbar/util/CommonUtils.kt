package com.andrognito.flashbar.util

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.view.*
import android.view.Surface.*
import com.andrognito.flashbar.util.NavigationBarPosition.*
import java.lang.reflect.InvocationTargetException

internal fun Activity.getStatusBarHeightInPx(): Int {
    val rectangle = Rect()

    window.decorView.getWindowVisibleDisplayFrame(rectangle)

    val statusBarHeight = rectangle.top
    val contentViewTop = window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
    return if (contentViewTop == 0) { //Actionbar is not present
        statusBarHeight
    } else {
        contentViewTop - statusBarHeight
    }
}

internal fun Activity.getNavigationBarPosition(): NavigationBarPosition {
    return when (windowManager.defaultDisplay.rotation) {
        ROTATION_0 -> BOTTOM
        ROTATION_90 -> RIGHT
        ROTATION_270 -> LEFT
        else -> TOP
    }
}

internal fun Activity.getNavigationBarSizeInPx(): Int {
    val realScreenSize = getRealScreenSize()
    val appUsableScreenSize = getAppUsableScreenSize()
    val navigationBarPosition = getNavigationBarPosition()

    return if (navigationBarPosition == LEFT || navigationBarPosition == RIGHT) {
        realScreenSize.x - appUsableScreenSize.x
    } else {
        realScreenSize.y - appUsableScreenSize.y
    }
}

internal fun Activity?.getRootView(): ViewGroup? {
    if (this == null || window == null || window.decorView == null) {
        return null
    }
    return window.decorView as ViewGroup
}

internal fun Context.convertDpToPx(dp: Int): Int {
    return Math.round(dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

internal fun Context.convertPxToDp(px: Int): Int {
    return Math.round(px / (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

private fun Activity.getRealScreenSize(): Point {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val defaultDisplay = windowManager.defaultDisplay
    val size = Point()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        defaultDisplay.getRealSize(size)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        try {
            size.x = Display::class.java.getMethod("getRawWidth").invoke(defaultDisplay) as Int
            size.y = Display::class.java.getMethod("getRawHeight").invoke(defaultDisplay) as Int
        } catch (e: IllegalAccessException) {
        } catch (e: InvocationTargetException) {
        } catch (e: NoSuchMethodException) {
        }
    }
    return size
}

private fun Activity.getAppUsableScreenSize(): Point {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val defaultDisplay = windowManager.defaultDisplay
    val size = Point()
    defaultDisplay.getSize(size)
    return size
}

inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
                f()
            }
        }
    })
}

internal enum class NavigationBarPosition {
    BOTTOM,
    RIGHT,
    LEFT,
    TOP
}

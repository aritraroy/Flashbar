package com.andrognito.flashbar.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.view.Display
import android.view.View
import android.view.Window
import android.view.WindowManager
import java.lang.reflect.InvocationTargetException

const val NAV_POSITION_BOTTOM = 0
const val NAV_POSITION_RIGHT = 1
const val NO_NAV_BAR = -1

internal fun getStatusBarHeightInPixels(activity: Activity): Int {
    val rectangle = Rect()
    val window = activity.window

    window.decorView.getWindowVisibleDisplayFrame(rectangle)

    val statusBarHeight = rectangle.top
    val contentViewTop = window.findViewById<View>(Window.ID_ANDROID_CONTENT).top

    return contentViewTop - statusBarHeight
}

internal fun getNavigationBarPostion(context: Context): Int {
    val appUsableSize = getAppUsableScreenSize(context)
    val realScreenSize = getRealScreenSize(context)

    if (realScreenSize.y > appUsableSize.y) return NAV_POSITION_BOTTOM
    if (realScreenSize.x > appUsableSize.x) return NAV_POSITION_RIGHT

    return NO_NAV_BAR
}

internal fun getNavigationBarHeightInPixels(context: Context): Int {
    val appUsableSize = getAppUsableScreenSize(context)
    val realScreenSize = getRealScreenSize(context)
    return realScreenSize.y - appUsableSize.y
}

internal fun getNavigationBarWidthInPixels(context: Context): Int {
    val appUsableSize = getAppUsableScreenSize(context)
    val realScreenSize = getRealScreenSize(context)
    return realScreenSize.x - appUsableSize.x
}

internal fun isOrientationLandscape(context: Context): Boolean =
        context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

internal fun isOrientationPortrait(context: Context): Boolean =
        context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

private fun getAppUsableScreenSize(context: Context): Point {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val defaultDisplay = windowManager.defaultDisplay
    val size = Point()
    defaultDisplay.getSize(size)
    return size
}

private fun getRealScreenSize(context: Context): Point {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
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
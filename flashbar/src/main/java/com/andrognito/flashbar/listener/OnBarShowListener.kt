package com.andrognito.flashbar.listener

import com.andrognito.flashbar.Flashbar

interface OnBarShowListener {
    fun onShowing(bar: Flashbar)
    fun onShown(bar: Flashbar)
}
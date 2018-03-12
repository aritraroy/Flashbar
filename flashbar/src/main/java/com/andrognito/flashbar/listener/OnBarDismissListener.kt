package com.andrognito.flashbar.listener

import com.andrognito.flashbar.Flashbar
import com.andrognito.flashbar.Flashbar.FlashbarDismissEvent

interface OnBarDismissListener {
    fun onDismissing(bar: Flashbar)
    fun onDismissed(bar: Flashbar, event: FlashbarDismissEvent)
}
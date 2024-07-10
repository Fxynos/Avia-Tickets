package com.vl.aviatickets.ui.entity

import okhttp3.internal.format

object Formatter {
    /**
     * Format digits to `XXX XXX`
     */
    fun formatPrice(value: Int): String = StringBuilder().apply {
        if (value >= 1000) {
            append(value / 1000)
            append(format(" %03d", value % 1000))
        } else append(value)
    }.toString()

    /**
     * Format to `XX:XX XX:XX`
     */
    fun formatTimeRange(range: List<String>): String = range.joinToString(separator = " ")
}
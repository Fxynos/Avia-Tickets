package com.vl.aviatickets.ui.entity

import okhttp3.internal.format
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Formatter {
    fun formatPrice(value: Int): String = StringBuilder().apply {
        if (value >= 1000) {
            append(value / 1000)
            append(format(" %03d", value % 1000))
        } else append(value)
    }.toString()

    fun formatTimeRange(range: List<String>): String = range.joinToString(separator = " ")

    fun formatTime(unixSec: Long): String = SimpleDateFormat(
        "HH:mm",
        Locale.getDefault()
    ).format(Date(unixSec * 1000))

    // TODO use string resources for all those beneath

    fun formatDate(date: String): String = Calendar.getInstance().run {
        time = SimpleDateFormat("yyyy-MM-DD", Locale.getDefault()).parse(date)!!

        val day = get(Calendar.DAY_OF_MONTH)
        val month = listOf(
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря"
        )[get(Calendar.MONTH)]

        "$day $month"
    }

    fun formatPassengers(count: Int): String {
        val form = when {
            count % 10 == 0 || count % 10 in 5..9 || count % 100 in 11..14 -> "пассажиров"
            count % 10 == 1 -> "пассажир"
            else -> "пассажира"
        }
        return "$count $form"
    }

    fun formatDuration(durationSec: Int, hasTransfer: Boolean): String {
        val duration = "${durationSec / 60 / 30 / 2f}ч"
        return if (hasTransfer) duration else "$duration / Без пересадок"
    }
}
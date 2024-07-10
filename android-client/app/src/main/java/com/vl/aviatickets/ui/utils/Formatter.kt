package com.vl.aviatickets.ui.utils

import okhttp3.internal.format
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Calendar
import java.util.Locale
import kotlin.math.round

// TODO use string resources for all those beneath
object Formatter {

    fun formatPrice(value: Int): String = StringBuilder().apply {
        if (value >= 1000) {
            append(value / 1000)
            append(format(" %03d", value % 1000))
        } else append(value)
    }.toString()

    fun formatPassengers(count: Int): String {
        val form = when {
            count % 10 == 0 || count % 10 in 5..9 || count % 100 in 11..14 -> "пассажиров"
            count % 10 == 1 -> "пассажир"
            else -> "пассажира"
        }
        return "$count $form"
    }

    fun formatDuration(durationSec: Int, hasTransfer: Boolean): String {
        val duration = "${round(durationSec / 60f / 30) / 2f}ч"
        return if (hasTransfer) duration else "$duration / Без пересадок"
    }

    /* DateTime */

    fun parseUnixTime(unixSec: Long): Calendar =
        Calendar.Builder()
            .setInstant(unixSec * 1000)
            .setLocale(Locale.getDefault())
            .build()

    fun parseDateTime(dateTime: String): Calendar =
        Calendar.Builder()
            .setInstant(
                SimpleDateFormat(
                    "yyyy-MM-DD'T'HH:mm:ss",
                    Locale.getDefault()
                ).parse(dateTime)!!.time
            ).build()

    /**
     * User-friendly day & month
     */
    fun formatUserDate(date: Calendar): String {
        val day = date[Calendar.DAY_OF_MONTH]
        val month = listOf(
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декабря"
        )[date[Calendar.MONTH]]

        return "$day $month"
    }

    fun formatUserDayOfWeek(date: Calendar): String = listOf(
        "пн", "вт", "ср", "чт", "пт", "сб", "вс"
    )[date[Calendar.DAY_OF_WEEK]]

    fun formatUserTime(date: Calendar): String = SimpleDateFormat(
        "HH:mm",
        Locale.getDefault()
    ).format(date.time)

    fun formatDateTime(date: Calendar): String =
        SimpleDateFormat(
            "yyyy-MM-DD'T'HH:mm:ss",
            Locale.getDefault()
        ).format(date.time)
}
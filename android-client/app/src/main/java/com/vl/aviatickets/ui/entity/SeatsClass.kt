package com.vl.aviatickets.ui.entity

import androidx.annotation.StringRes
import com.vl.aviatickets.R

enum class SeatsClass(@StringRes val title: Int) {
    ECONOMY(R.string.seats_class_economy),
    PREMIUM(R.string.seats_class_premium),
    BUSINESS(R.string.seats_class_business),
    FIRST(R.string.seats_class_first)
}
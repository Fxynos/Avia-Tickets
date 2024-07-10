package com.vl.aviatickets.ui.entity

import com.vl.aviatickets.domain.entity.TicketsOffer

sealed interface FlightsItem {
    data object Loading: FlightsItem
    data class Loaded(val flight: TicketsOffer): FlightsItem
}
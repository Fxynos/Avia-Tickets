package com.vl.aviatickets.ui.entity

import com.vl.aviatickets.domain.entity.Ticket

sealed interface TicketsItem {
    data object Loading: TicketsItem
    data class Loaded(val ticket: Ticket): TicketsItem
}
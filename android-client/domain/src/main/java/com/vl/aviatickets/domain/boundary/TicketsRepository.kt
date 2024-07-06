package com.vl.aviatickets.domain.boundary

import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.domain.entity.Ticket

interface TicketsRepository {
    fun searchTickets(route: Route): List<Ticket>
}
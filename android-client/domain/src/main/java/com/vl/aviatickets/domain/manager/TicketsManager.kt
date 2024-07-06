package com.vl.aviatickets.domain.manager

import com.vl.aviatickets.domain.boundary.TicketsOffersRepository
import com.vl.aviatickets.domain.boundary.TicketsRepository
import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.domain.entity.Ticket
import com.vl.aviatickets.domain.entity.TicketsOffer

class TicketsManager(
    private val ticketsRepo: TicketsRepository,
    private val ticketsOffersRepo: TicketsOffersRepository
) {
    fun searchTickets(route: Route): List<Ticket> =
        ticketsRepo.searchTickets(route)

    fun searchTicketsOffers(route: Route): List<TicketsOffer> =
        ticketsOffersRepo.searchTicketsOffers(route)
}
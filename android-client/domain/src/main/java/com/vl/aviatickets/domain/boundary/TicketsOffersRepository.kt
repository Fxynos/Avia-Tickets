package com.vl.aviatickets.domain.boundary

import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.domain.entity.TicketsOffer

interface TicketsOffersRepository {
    fun searchTicketsOffers(route: Route): List<TicketsOffer>
}
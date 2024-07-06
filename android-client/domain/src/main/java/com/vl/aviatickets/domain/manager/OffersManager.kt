package com.vl.aviatickets.domain.manager

import com.vl.aviatickets.domain.boundary.OffersSupplier
import com.vl.aviatickets.domain.entity.Offer

class OffersManager(private val offersSupplier: OffersSupplier) {
    fun getOffers(): List<Offer> = offersSupplier.getOffers()
}
package com.vl.aviatickets.domain.boundary

import com.vl.aviatickets.domain.entity.Offer

interface OffersSupplier {
    fun getOffers(): List<Offer>
}
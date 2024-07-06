package com.vl.aviatickets.domain.boundary

import com.vl.aviatickets.domain.entity.Offer

interface OffersRepository {
    fun getAllOffers(): List<Offer>
}
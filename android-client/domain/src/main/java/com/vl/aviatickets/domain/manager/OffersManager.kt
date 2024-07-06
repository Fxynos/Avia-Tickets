package com.vl.aviatickets.domain.manager

import com.vl.aviatickets.domain.boundary.OffersRepository
import com.vl.aviatickets.domain.entity.Offer

class OffersManager(private val offersStore: OffersRepository) {
    fun getOffers(): List<Offer> = offersStore.getAllOffers()
}
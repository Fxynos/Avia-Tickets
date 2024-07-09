package com.vl.aviatickets.ui.entity

import com.vl.aviatickets.domain.entity.Offer

sealed interface OffersItem {
    data object Loading: OffersItem
    data class Loaded(val offer: Offer): OffersItem
}
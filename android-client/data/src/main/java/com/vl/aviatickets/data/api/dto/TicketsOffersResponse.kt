package com.vl.aviatickets.data.api.dto

import com.google.gson.annotations.SerializedName

internal class TicketsOffersResponse {
    @SerializedName("tickets_offers")
    val ticketsOffers: List<TicketsOfferDto> = emptyList()
}
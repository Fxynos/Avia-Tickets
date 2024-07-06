package com.vl.aviatickets.data.api.dto

import com.google.gson.annotations.SerializedName

internal class TicketDto {
    var id: Int = 0
    var badge: String? = null
    lateinit var price: PriceDto
    lateinit var departure: TimeAndLocationDto
    lateinit var arrival: TimeAndLocationDto
    @SerializedName("has_transfer")
    var hasTransfer: Boolean = false
}
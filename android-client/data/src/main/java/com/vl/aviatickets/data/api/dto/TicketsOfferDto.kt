package com.vl.aviatickets.data.api.dto

import com.google.gson.annotations.SerializedName

internal class TicketsOfferDto {
    var id: Int = 0
    var title: String = ""
    @SerializedName("time_range")
    var timeRange: List<String> = emptyList()
    lateinit var price: PriceDto
}
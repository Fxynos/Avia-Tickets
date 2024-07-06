package com.vl.aviatickets.data.api.dto

import com.google.gson.annotations.SerializedName

internal class OfferDto {
    var id: Int = 0
    var title: String = ""
    var town: String = ""
    @SerializedName("image_url")
    var imageUrl: String? = null
    lateinit var price: PriceDto
}
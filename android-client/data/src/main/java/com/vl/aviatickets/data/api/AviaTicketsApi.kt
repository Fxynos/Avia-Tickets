package com.vl.aviatickets.data.api

import com.vl.aviatickets.data.api.dto.OffersResponse
import com.vl.aviatickets.data.api.dto.TicketsOffersResponse
import com.vl.aviatickets.data.api.dto.TicketsResponse
import retrofit2.Call
import retrofit2.http.GET

internal interface AviaTicketsApi {
    @GET("offers") fun getOffers(): Call<OffersResponse>
    @GET("tickets_offers") fun getTicketsOffers(): Call<TicketsOffersResponse>
    @GET("tickets") fun getTickets(): Call<TicketsResponse>
}
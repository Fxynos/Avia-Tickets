package com.vl.aviatickets.data.api

import com.vl.aviatickets.BuildConfig
import com.vl.aviatickets.domain.boundary.TicketsRepository
import com.vl.aviatickets.domain.boundary.OffersRepository
import com.vl.aviatickets.domain.boundary.TicketsOffersRepository
import com.vl.aviatickets.domain.entity.TicketsOffer
import com.vl.aviatickets.domain.entity.Offer
import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.domain.entity.Ticket
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.Calendar

private val BASE_URL: String = BuildConfig.API_BASE_URL.let { url ->
    if (url.endsWith('/')) url else "$url/"
}

object AviaTicketsDao: OffersRepository, TicketsRepository, TicketsOffersRepository {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AviaTicketsApi::class.java)

    override fun getAllOffers(): List<Offer> =
        api.getOffers().handle().offers.map {
            Offer(it.id, it.title, it.town, it.price.value, it.imageUrl)
        }

    override fun searchTickets(route: Route): List<Ticket> =
        api.getTickets().handle().tickets.map {
            Ticket(
                it.id,
                it.badge,
                it.price.value,
                it.hasTransfer,
                it.departure.town,
                parseDateToUnixSeconds(it.departure.date),
                it.departure.airport,
                it.arrival.town,
                parseDateToUnixSeconds(it.arrival.date),
                it.arrival.airport
            )
        }

    override fun searchTicketsOffers(route: Route): List<TicketsOffer> =
        api.getTicketsOffers().handle().ticketsOffers.map {
            TicketsOffer(it.id, it.title, it.price.value, it.timeRange)
        }

    private fun <T> Call<T>.handle(): T = execute().run {
        if (!isSuccessful)
            throw AviaTicketsApiException(errorBody())

        body()!!
    }
}

private fun parseDateToUnixSeconds(dateTime: String): Long =
    Calendar.getInstance().apply {
        time = DateFormat.getDateInstance().parse(dateTime)!!
    }.timeInMillis / 1000
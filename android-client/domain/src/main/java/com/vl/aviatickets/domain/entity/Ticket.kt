package com.vl.aviatickets.domain.entity

/**
 * @param price in rubles
 * @param departureTime unix time in seconds
 * @param arrivalTime unix time in seconds
 */
data class Ticket(
    val id: Int,
    val badge: String?,
    val price: Int,
    val hasTransfer: Boolean,
    val departureTown: String,
    val departureTime: Long,
    val departureAirport: String,
    val arrivalTown: String,
    val arrivalTime: Long,
    val arrivalAirport: String
)
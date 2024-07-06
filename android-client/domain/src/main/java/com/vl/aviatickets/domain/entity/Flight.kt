package com.vl.aviatickets.domain.entity

/**
 * @param price in rubles
 * @param timeRange list of strings matching format "HH:mm"
 */
data class Flight(
    val id: Int,
    val title: String,
    val price: Int,
    val timeRange: List<String>
)
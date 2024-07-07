package com.vl.aviatickets.domain.entity

/**
 * @param price in rubles
 */
data class Offer(
    val id: Int,
    val title: String,
    val town: String,
    val price: Int,
    val imageUrl: String?
)
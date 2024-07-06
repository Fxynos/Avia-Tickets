package com.vl.aviatickets.domain.manager

import com.vl.aviatickets.domain.boundary.HistoryCache
import java.util.LinkedList

class HistoryManager(
    private val cache: HistoryCache<String>,
    private val maxCapacity: Int
) {
    val lastDepartureCity by cache::lastDepartureCity
    val lastArrivalCities by cache::lastArrivalCities

    fun saveDepartureCity(city: String) {
        cache.lastDepartureCity = city
    }

    fun saveArrivalCity(city: String) {
        val history = LinkedList(cache.lastArrivalCities ?: emptyList())
        history.add(city)

        while (history.size > maxCapacity)
            history.removeFirst()

        cache.lastArrivalCities = history
    }
}
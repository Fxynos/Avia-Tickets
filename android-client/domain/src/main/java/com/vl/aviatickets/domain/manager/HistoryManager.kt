package com.vl.aviatickets.domain.manager

import com.vl.aviatickets.domain.boundary.Cache
import java.util.LinkedList

private const val KEY_DEPARTURE_CITY = "dep"
private const val KEY_ARRIVAL_CITY = "arr"

class HistoryManager(
    private val cache: Cache<String>,
    private val maxCapacity: Int
) {
    val lastDepartureCity: String? get() = cache[KEY_DEPARTURE_CITY]

    fun saveDepartureCity(city: String) {
        cache[KEY_DEPARTURE_CITY] = city
    }

    fun saveArrivalCity(city: String) {
        val history = LinkedList(cache.getList(KEY_ARRIVAL_CITY) ?: emptyList())
        history.add(city)

        while (history.size > maxCapacity)
            history.removeFirst()

        cache.setList(KEY_ARRIVAL_CITY, history)
    }
}
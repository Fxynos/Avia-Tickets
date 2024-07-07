package com.vl.aviatickets.domain.manager

import com.vl.aviatickets.domain.boundary.HistoryStore
import java.util.LinkedList

class HistoryManager(
    private val cache: HistoryStore,
    private val maxCapacity: Int
) {
    val lastDepartureTown by cache::lastDepartureTown
    val lastArrivalTowns by cache::lastArrivalTowns

    fun saveDepartureTown(town: String) {
        cache.lastDepartureTown = town
    }

    fun saveArrivalTown(town: String) {
        val history = LinkedList(cache.lastArrivalTowns ?: emptyList())
        history.add(town)

        while (history.size > maxCapacity)
            history.removeFirst()

        cache.lastArrivalTowns = history
    }
}
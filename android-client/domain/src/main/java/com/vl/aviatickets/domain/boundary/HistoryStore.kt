package com.vl.aviatickets.domain.boundary

interface HistoryStore {
    var lastDepartureCity: String?
    var lastArrivalCities: List<String>?
}
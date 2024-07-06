package com.vl.aviatickets.domain.boundary

interface HistoryCache<T: Any> {
    var lastDepartureCity: String?
    var lastArrivalCities: List<String>?
}
package com.vl.aviatickets.domain.boundary

interface HistoryStore {
    var lastDepartureTown: String?
    var lastArrivalTowns: List<String>?
}
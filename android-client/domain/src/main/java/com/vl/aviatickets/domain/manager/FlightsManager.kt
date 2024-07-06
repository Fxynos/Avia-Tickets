package com.vl.aviatickets.domain.manager

import com.vl.aviatickets.domain.boundary.FlightsTimetable
import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.domain.entity.Flight

class FlightsManager(private val timetable: FlightsTimetable) {
    fun getTimetable(route: Route): List<Flight> = timetable.search(route)
}
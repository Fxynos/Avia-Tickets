package com.vl.aviatickets.domain.boundary

import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.domain.entity.Flight

interface FlightsTimetable {
    fun search(route: Route): List<Flight>
}
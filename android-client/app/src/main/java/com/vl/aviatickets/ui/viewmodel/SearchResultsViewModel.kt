package com.vl.aviatickets.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.domain.manager.TicketsManager
import com.vl.aviatickets.ui.entity.FlightsItem
import com.vl.aviatickets.ui.entity.SeatsClass
import com.vl.aviatickets.ui.utils.Formatter
import com.vl.aviatickets.ui.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import java.util.Calendar
import javax.inject.Inject

private const val TAG = "SearchResults"
private const val RETRY_DELAY = 500L

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val manager: TicketsManager
): ViewModel() {
    private var searchJob: Job? = null
    private var flightDate: Calendar = Formatter.parseUnixTime(
        System.currentTimeMillis() / 1000
    )

    private val _uiState = MutableStateFlow(UiState(
        flights = emptyList(),
        route = Route("", ""),
        isRouteValid = false,
        isSearching = false,
        passengers = 1,
        seats = SeatsClass.ECONOMY,
        date = Formatter.formatUserDate(flightDate),
        dayOfWeek = Formatter.formatUserDayOfWeek(flightDate)
    ))
    val uiState = _uiState.asStateFlow()

    private val _searchResultEvent = MutableSharedFlow<SearchResult>()
    val searchResultEvent = _searchResultEvent.asSharedFlow()

    fun search(route: Route) {
        if (uiState.value.isSearching) // cancel current request and execute new one
            searchJob?.cancel()

        if (!validateRoute(route)) {
            _uiState.update {
                it.copy(
                    route = route,
                    isRouteValid = false
                )
            }
            return
        }

        _uiState.update {
            it.copy(
                flights = listOf(FlightsItem.Loading, FlightsItem.Loading, FlightsItem.Loading),
                route = route,
                isRouteValid = true,
                isSearching = true,
            )
        }

        searchJob = viewModelScope.launch {
            var flights: List<FlightsItem>? = null

            withContext(Dispatchers.IO) {
                while (flights == null) try {
                    flights = manager.searchTicketsOffers(route).map(FlightsItem::Loaded)
                } catch (e: IOException) {
                    Log.w(TAG, "Couldn't load flights: ${e.message ?: e.toString()}")
                    delay(RETRY_DELAY)
                }
            }

            _uiState.update {
                it.copy(
                    flights = flights!!,
                    isSearching = false
                )
            }
        }
    }

    fun openTickets() {
        val state = uiState.value

        if (state.isRouteValid)
            emitEvent(SearchResult.NavigateToTickets(
                state.route,
                Formatter.formatDateTime(flightDate),
                _uiState.value.passengers
            ))
    }

    fun setPassengers(count: Int, seats: SeatsClass) {
        _uiState.update {
            it.copy(
                passengers = count,
                seats = seats
            )
        }
    }

    fun setFlightDate(dateTime: String) {
        flightDate = Formatter.parseDateTime(dateTime)
        _uiState.update {
            it.copy(
                date = Formatter.formatUserDate(flightDate),
                dayOfWeek = Formatter.formatUserDayOfWeek(flightDate)
            )
        }
    }

    /**
     * @return `true` if valid, otherwise emits event to notify user and returns false
     */
    private fun validateRoute(route: Route): Boolean {
        if (!Validator.isTownValid(route.departureTown)) {
            emitEvent(SearchResult.InvalidTown(isDepartureTown = true))
            return false
        }

        if (!Validator.isTownValid(route.arrivalTown)) {
            emitEvent(SearchResult.InvalidTown(isDepartureTown = false))
            return false
        }

        return true
    }

    private fun emitEvent(event: SearchResult) {
        viewModelScope.launch { _searchResultEvent.emit(event) }
    }

    sealed interface SearchResult {

        data class InvalidTown(val isDepartureTown: Boolean): SearchResult

        /**
         * @param date `yyyy-MM-DD`
         */
        data class NavigateToTickets(
            val route: Route,
            val date: String,
            val passengers: Int
        ): SearchResult
    }

    /**
     * @param isSearching [search] available only on `false`
     * @param isRouteValid [openTickets] available only on `true`
     * @param date date in user-friendly format
     */
    data class UiState(
        val flights: List<FlightsItem>,
        val route: Route,
        val isRouteValid: Boolean,
        val isSearching: Boolean,
        val passengers: Int,
        val seats: SeatsClass,
        val date: String,
        val dayOfWeek: String
    )
}
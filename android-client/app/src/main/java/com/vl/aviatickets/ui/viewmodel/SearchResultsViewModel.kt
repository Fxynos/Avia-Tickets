package com.vl.aviatickets.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.domain.manager.TicketsManager
import com.vl.aviatickets.ui.entity.FlightsItem
import com.vl.aviatickets.ui.entity.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
    private val manager: TicketsManager
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState(
        flights = emptyList(),
        route = Route("", ""),
        isRouteValid = false,
        isSearching = false
    ))
    val uiState = _uiState.asStateFlow()

    private val _searchResultEvent = MutableSharedFlow<SearchResult>()
    val searchResultEvent = _searchResultEvent.asSharedFlow()

    fun search(route: Route) {
        if (uiState.value.isSearching)
            return

        if (!validate(route)) {
            _uiState.tryEmit(uiState.value.copy(
                route = route,
                isRouteValid = false
            ))
            return
        }

        _uiState.tryEmit(UiState(
            flights = listOf(FlightsItem.Loading, FlightsItem.Loading, FlightsItem.Loading),
            route = route,
            isRouteValid = true,
            isSearching = true
        ))

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(UiState(
                flights = manager.searchTicketsOffers(route).map(FlightsItem::Loaded),
                route = route,
                isRouteValid = true,
                isSearching = false
            ))
        }
    }

    fun openTickets() {
        val state = uiState.value

        if (state.isRouteValid)
            emitEvent(SearchResult.NavigateToTickets(
                state.route,
                "2024-02-23",
                1
            ))
    }

    /**
     * @return `true` if valid, otherwise emits event to notify user and returns false
     */
    private fun validate(route: Route): Boolean {
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
        data class NavigateToTickets(
            val route: Route,
            val date: String,
            val passengers: Int
        ): SearchResult
    }

    /**
     * @param isSearching [search] available only on `false`
     * @param isRouteValid [openTickets] available only on `true`
     */
    data class UiState(
        val flights: List<FlightsItem>,
        val route: Route,
        val isRouteValid: Boolean,
        val isSearching: Boolean
    )
}
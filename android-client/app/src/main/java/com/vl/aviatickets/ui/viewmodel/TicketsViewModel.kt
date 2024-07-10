package com.vl.aviatickets.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.domain.manager.TicketsManager
import com.vl.aviatickets.ui.entity.Formatter
import com.vl.aviatickets.ui.entity.TicketsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val manager: TicketsManager
): ViewModel() {
    private val _uiState = MutableStateFlow(UiState(
        isInitialized = false,
        route = "",
        details = "",
        tickets = emptyList()
    ))
    val uiState = _uiState.asStateFlow()

    fun initialize(
        route: Route,
        passengers: Int,
        date: String
    ) {
        if (uiState.value.isInitialized)
            return

        val previewState = _uiState.value.copy(
            isInitialized = true,
            route = "${route.departureTown}-${route.arrivalTown}",
            details = "${Formatter.formatDate(date)}, ${Formatter.formatPassengers(passengers)}",
            tickets = (1..6).mapTo(ArrayList()) { TicketsItem.Loading }
        )

        _uiState.tryEmit(previewState)

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(previewState.copy(
                tickets = manager.searchTickets(route)
                    .map(TicketsItem::Loaded)
            ))
        }
    }

    data class UiState(
        val route: String,
        val details: String,
        val tickets: List<TicketsItem>,
        val isInitialized: Boolean
    )
}
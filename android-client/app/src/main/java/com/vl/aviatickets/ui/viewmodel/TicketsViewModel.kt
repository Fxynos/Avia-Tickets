package com.vl.aviatickets.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.domain.manager.TicketsManager
import com.vl.aviatickets.ui.utils.Formatter
import com.vl.aviatickets.ui.entity.TicketsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

private const val TAG = "Tickets"
private const val RETRY_DELAY = 500L

@HiltViewModel
class TicketsViewModel @Inject constructor(
    private val manager: TicketsManager
): ViewModel() {
    private var isInitialized = false

    private val _uiState = MutableStateFlow(UiState(
        route = "",
        details = "",
        tickets = emptyList()
    ))
    val uiState = _uiState.asStateFlow()

    fun initialize(
        route: Route,
        passengers: Int,
        dateTime: String
    ) {
        if (isInitialized)
            return

        isInitialized = true

        val previewState = _uiState.value.copy(
            route = "${route.departureTown}-${route.arrivalTown}",
            details = "${
                Formatter.formatUserDate(Formatter.parseDateTime(dateTime))
            }, ${
                Formatter.formatPassengers(passengers)
            }",
            tickets = (1..6).mapTo(ArrayList()) { TicketsItem.Loading }
        )

        _uiState.tryEmit(previewState)

        viewModelScope.launch(Dispatchers.IO) {
            while (true) try {
                _uiState.emit(previewState.copy(
                    tickets = manager.searchTickets(route)
                        .map(TicketsItem::Loaded)
                ))
                return@launch
            } catch (e: IOException) {
                Log.w(TAG, "Couldn't load tickets: ${e.message ?: e.toString()}")
                delay(RETRY_DELAY)
            }
        }
    }

    data class UiState(
        val route: String,
        val details: String,
        val tickets: List<TicketsItem>
    )
}
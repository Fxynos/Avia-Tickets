package com.vl.aviatickets.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.domain.manager.HistoryManager
import com.vl.aviatickets.domain.manager.OffersManager
import com.vl.aviatickets.ui.entity.OffersItem
import com.vl.aviatickets.ui.entity.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.IOException
import java.util.LinkedList
import javax.inject.Inject

private const val TAG = "Offers"
private const val RETRY_DELAY = 500L

@HiltViewModel
class OffersViewModel @Inject constructor(
    private val historyManager: HistoryManager,
    offersManager: OffersManager
): ViewModel() {
    val isFirstTime: Boolean get() = historyManager.lastDepartureTown == null
    val defaultDepartureTown: String get() = historyManager.lastDepartureTown ?: ""
    val recommendedArrivalTowns: List<String>
        get() = LinkedList(historyManager.lastArrivalTowns?.asReversed() ?: emptyList()).apply {
            val defaultDestinations = listOf("Стамбул", "Сочи", "Пхукет").iterator()
            while (size < 3) add(defaultDestinations.next())
        }
    val offersState: StateFlow<List<OffersItem>> = flow {
            while (true) try {
                emit(offersManager.getOffers().map(OffersItem::Loaded))
                return@flow
            } catch (e: IOException) {
                Log.w(TAG, "Couldn't load offers: ${e.message ?: e.toString()}")
                delay(RETRY_DELAY)
            }
        }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            listOf(OffersItem.Loading, OffersItem.Loading, OffersItem.Loading)
        )

    private val _validationResultEvents = MutableSharedFlow<ValidationResult>()
    val validationResultEvents = _validationResultEvents.asSharedFlow()

    private lateinit var departureTown: String

    init {
        viewModelScope.launch { validationResultEvents.collect {
            Log.d(TAG, "Event: $it")
        } }
    }

    fun chooseDepartureTown(town: String) {
        if (!Validator.isTownValid(town)) {
            emitEvent(ValidationResult.InvalidTown(isDepartureTown = true))
            return
        }

        departureTown = town
        historyManager.saveDepartureTown(town)
        emitEvent(ValidationResult.NavigateToChoosingArrivalTown(town))
    }

    fun chooseArrivalTown(town: String) {
        if (!Validator.isTownValid(town)) {
            emitEvent(ValidationResult.InvalidTown(isDepartureTown = false))
            return
        }

        historyManager.saveArrivalTown(town)
        emitEvent(ValidationResult.NavigateToSearch(Route(
            departureTown = departureTown,
            arrivalTown = town
        )))
    }

    private fun emitEvent(event: ValidationResult) {
        viewModelScope.launch { _validationResultEvents.emit(event) }
    }

    sealed interface ValidationResult {
        data class InvalidTown(val isDepartureTown: Boolean): ValidationResult
        data class NavigateToChoosingArrivalTown(val departureTown: String): ValidationResult
        data class NavigateToSearch(val route: Route): ValidationResult
    }
}
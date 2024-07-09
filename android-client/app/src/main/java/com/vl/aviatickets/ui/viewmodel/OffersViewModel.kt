package com.vl.aviatickets.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vl.aviatickets.domain.entity.Offer
import com.vl.aviatickets.domain.manager.HistoryManager
import com.vl.aviatickets.domain.manager.OffersManager
import com.vl.aviatickets.ui.entity.OffersItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import okio.IOException
import javax.inject.Inject

private const val TAG = "Offers"
private const val RETRY_DELAY = 500L

@HiltViewModel
class OffersViewModel @Inject constructor(
    private val historyManager: HistoryManager,
    offersManager: OffersManager
): ViewModel() {
    val defaultDepartureTown: String get() = historyManager.lastDepartureTown ?: ""
    val offersState: StateFlow<List<OffersItem>> = flow {
            while (true) try {
                emit(offersManager.getOffers().map(OffersItem::Loaded))
                return@flow
            } catch (e: IOException) {
                Log.w(TAG, e.stackTraceToString())
                delay(RETRY_DELAY)
            }
        }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            listOf(OffersItem.Loading, OffersItem.Loading, OffersItem.Loading)
        )

    fun setDepartureTown(town: String) = historyManager.saveDepartureTown(town)
}
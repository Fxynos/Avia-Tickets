package com.vl.aviatickets.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vl.aviatickets.domain.boundary.HistoryStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val PREFERENCES_FILE = "cache"

private val KEY_DEPARTURE_TOWN = stringPreferencesKey("departure_towns")
private val KEY_ARRIVAL_TOWNS = stringSetPreferencesKey("arrival_towns")
private val Context.dataStore by preferencesDataStore(PREFERENCES_FILE)

class HistoryStoreImpl(private val context: Context): HistoryStore {

    private val dataStore: DataStore<Preferences> get() = context.dataStore
    private val scope = CoroutineScope(Dispatchers.IO)
    private val cacheState: StateFlow<CacheState> = dataStore.data.map { prefs ->
        CacheState.Present(
            departureTown = prefs[KEY_DEPARTURE_TOWN],
            arrivalTowns = prefs[KEY_ARRIVAL_TOWNS]?.toList()
        )
    }.stateIn(scope, SharingStarted.Eagerly, CacheState.Loading)

    override var lastDepartureTown: String?
        get() = (cacheState.value as? CacheState.Present)?.departureTown
        set(value) {
            editPreferences {
                if (value == null)
                    it.remove(KEY_DEPARTURE_TOWN)
                else
                    it[KEY_DEPARTURE_TOWN] = value
            }
        }
    override var lastArrivalTowns: List<String>?
        get() = (cacheState.value as? CacheState.Present)?.arrivalTowns
        set(value) {
            editPreferences {
                if (value == null)
                    it.remove(KEY_ARRIVAL_TOWNS)
                else
                    it[KEY_ARRIVAL_TOWNS] = value.toSet()
            }
        }

    private fun editPreferences(block: (MutablePreferences) -> Unit) {
        scope.launch {
            dataStore.edit(block)
        }
    }

    private sealed interface CacheState {
        data object Loading: CacheState
        class Present(val departureTown: String?, val arrivalTowns: List<String>?): CacheState
    }
}
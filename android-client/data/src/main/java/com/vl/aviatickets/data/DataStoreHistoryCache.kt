package com.vl.aviatickets.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vl.aviatickets.domain.boundary.HistoryCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val PREFERENCES_FILE = "cache"

private val KEY_DEPARTURE_CITY = stringPreferencesKey("departure_city")
private val KEY_ARRIVAL_CITIES = stringSetPreferencesKey("arrival_cities")
private val Context.dataStore by preferencesDataStore(PREFERENCES_FILE)

class DataStoreHistoryCache(private val context: Context): HistoryCache<String> {

    private val dataStore: DataStore<Preferences> get() = context.dataStore
    private val scope = CoroutineScope(Dispatchers.IO)
    private val cacheState: StateFlow<CacheState> = dataStore.data.map { prefs ->
        CacheState.Present(
            departureCity = prefs[KEY_DEPARTURE_CITY],
            arrivalCities = prefs[KEY_ARRIVAL_CITIES]?.toList()
        )
    }.stateIn(scope, SharingStarted.Eagerly, CacheState.Loading)

    override var lastDepartureCity: String?
        get() = (cacheState.value as? CacheState.Present)?.departureCity
        set(value) {
            editPreferences {
                if (value == null)
                    it.remove(KEY_DEPARTURE_CITY)
                else
                    it[KEY_DEPARTURE_CITY] = value
            }
        }
    override var lastArrivalCities: List<String>?
        get() = (cacheState.value as? CacheState.Present)?.arrivalCities
        set(value) {
            editPreferences {
                if (value == null)
                    it.remove(KEY_ARRIVAL_CITIES)
                else
                    it[KEY_ARRIVAL_CITIES] = value.toSet()
            }
        }

    private fun editPreferences(block: (MutablePreferences) -> Unit) {
        scope.launch {
            dataStore.edit(block)
        }
    }

    private interface CacheState {
        object Loading: CacheState
        class Present(val departureCity: String?, val arrivalCities: List<String>?): CacheState
    }
}
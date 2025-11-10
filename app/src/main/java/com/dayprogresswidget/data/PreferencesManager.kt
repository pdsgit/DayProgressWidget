package com.dayprogresswidget.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * PreferencesManager handles persistent storage of user preferences using DataStore.
 * This class stores wake-up time and sleep time in minutes from midnight.
 * 
 * DataStore is preferred over SharedPreferences for its coroutine-based API
 * and type safety.
 */
class PreferencesManager(private val context: Context) {
    
    companion object {
        // Extension property to create/access the DataStore instance
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = "day_progress_preferences"
        )
        
        // Keys for storing preferences
        private val WAKE_TIME_KEY = intPreferencesKey("wake_time")
        private val SLEEP_TIME_KEY = intPreferencesKey("sleep_time")
        
        // Default values: 7:00 AM wake, 11:00 PM sleep
        const val DEFAULT_WAKE_TIME = 7 * 60  // 7:00 AM in minutes from midnight
        const val DEFAULT_SLEEP_TIME = 23 * 60  // 11:00 PM in minutes from midnight
    }
    
    /**
     * Flow that emits the wake-up time in minutes from midnight.
     * Default is 7:00 AM (420 minutes).
     */
    val wakeTimeFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[WAKE_TIME_KEY] ?: DEFAULT_WAKE_TIME
    }
    
    /**
     * Flow that emits the sleep time in minutes from midnight.
     * Default is 11:00 PM (1380 minutes).
     */
    val sleepTimeFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[SLEEP_TIME_KEY] ?: DEFAULT_SLEEP_TIME
    }
    
    /**
     * Saves the wake-up time to DataStore.
     * @param timeInMinutes Time in minutes from midnight (0-1439)
     */
    suspend fun saveWakeTime(timeInMinutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[WAKE_TIME_KEY] = timeInMinutes
        }
    }
    
    /**
     * Saves the sleep time to DataStore.
     * @param timeInMinutes Time in minutes from midnight (0-1439)
     */
    suspend fun saveSleepTime(timeInMinutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[SLEEP_TIME_KEY] = timeInMinutes
        }
    }
}

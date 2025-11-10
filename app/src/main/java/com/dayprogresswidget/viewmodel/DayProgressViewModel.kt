package com.dayprogresswidget.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dayprogresswidget.data.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * ViewModel for the Day Progress screen.
 * Manages the state of wake/sleep times and calculates day progress.
 * 
 * This ViewModel:
 * - Loads and saves wake/sleep times using PreferencesManager
 * - Calculates the current progress of the day
 * - Provides time remaining until sleep
 * - Updates in real-time as the current time changes
 */
class DayProgressViewModel(application: Application) : AndroidViewModel(application) {
    
    private val preferencesManager = PreferencesManager(application)
    
    // Current time flow that updates every second (simulated with manual updates)
    private val currentTimeFlow = MutableStateFlow(getCurrentTimeInMinutes())
    
    /**
     * UI State that combines all the data needed for the screen.
     * This state is automatically updated when preferences or current time changes.
     */
    val uiState: StateFlow<DayProgressUiState> = combine(
        preferencesManager.wakeTimeFlow,
        preferencesManager.sleepTimeFlow,
        currentTimeFlow
    ) { wakeTime, sleepTime, currentTime ->
        calculateUiState(wakeTime, sleepTime, currentTime)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DayProgressUiState()
    )
    
    /**
     * Updates the current time. Call this method periodically to refresh the UI.
     */
    fun updateCurrentTime() {
        currentTimeFlow.value = getCurrentTimeInMinutes()
    }
    
    /**
     * Saves the wake-up time selected by the user.
     * @param hour Hour in 24-hour format (0-23)
     * @param minute Minute (0-59)
     */
    fun setWakeTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            val timeInMinutes = hour * 60 + minute
            preferencesManager.saveWakeTime(timeInMinutes)
            updateCurrentTime()
        }
    }
    
    /**
     * Saves the sleep time selected by the user.
     * @param hour Hour in 24-hour format (0-23)
     * @param minute Minute (0-59)
     */
    fun setSleepTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            val timeInMinutes = hour * 60 + minute
            preferencesManager.saveSleepTime(timeInMinutes)
            updateCurrentTime()
        }
    }
    
    /**
     * Calculates the UI state based on wake time, sleep time, and current time.
     */
    private fun calculateUiState(
        wakeTime: Int,
        sleepTime: Int,
        currentTime: Int
    ): DayProgressUiState {
        // Calculate total awake time in minutes
        val totalAwakeTime = if (sleepTime > wakeTime) {
            sleepTime - wakeTime
        } else {
            // Handle case where sleep time is after midnight
            (24 * 60 - wakeTime) + sleepTime
        }
        
        // Calculate elapsed time since wake
        val elapsedTime = if (currentTime >= wakeTime) {
            if (sleepTime > wakeTime) {
                // Same day
                minOf(currentTime - wakeTime, totalAwakeTime)
            } else {
                // Sleep time is after midnight
                if (currentTime < sleepTime) {
                    // Past midnight but before sleep
                    (24 * 60 - wakeTime) + currentTime
                } else {
                    // Before wake time
                    0
                }
            }
        } else {
            // Before wake time
            if (sleepTime < wakeTime && currentTime < sleepTime) {
                // Past midnight, before sleep
                (24 * 60 - wakeTime) + currentTime
            } else {
                0
            }
        }
        
        // Calculate progress percentage
        val progress = if (totalAwakeTime > 0) {
            (elapsedTime.toFloat() / totalAwakeTime.toFloat()).coerceIn(0f, 1f)
        } else {
            0f
        }
        
        // Calculate time remaining until sleep
        val timeRemaining = (totalAwakeTime - elapsedTime).coerceAtLeast(0)
        val hoursRemaining = timeRemaining / 60
        val minutesRemaining = timeRemaining % 60
        
        return DayProgressUiState(
            wakeTimeHour = wakeTime / 60,
            wakeTimeMinute = wakeTime % 60,
            sleepTimeHour = sleepTime / 60,
            sleepTimeMinute = sleepTime % 60,
            progress = progress,
            hoursRemaining = hoursRemaining,
            minutesRemaining = minutesRemaining
        )
    }
    
    /**
     * Gets the current time in minutes from midnight.
     */
    private fun getCurrentTimeInMinutes(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
    }
}

/**
 * Data class representing the UI state for the Day Progress screen.
 */
data class DayProgressUiState(
    val wakeTimeHour: Int = 7,
    val wakeTimeMinute: Int = 0,
    val sleepTimeHour: Int = 23,
    val sleepTimeMinute: Int = 0,
    val progress: Float = 0f,
    val hoursRemaining: Int = 16,
    val minutesRemaining: Int = 0
)

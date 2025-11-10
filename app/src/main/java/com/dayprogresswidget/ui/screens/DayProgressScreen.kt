package com.dayprogresswidget.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dayprogresswidget.ui.components.CircularProgressRing
import com.dayprogresswidget.ui.components.TimePickerField
import com.dayprogresswidget.viewmodel.DayProgressViewModel
import kotlinx.coroutines.delay

/**
 * Main screen of the Day Progress Widget app.
 * 
 * This screen displays:
 * - A circular progress ring showing day completion
 * - Time picker fields for wake and sleep times
 * - Time remaining until sleep
 * 
 * The screen updates every minute to reflect current progress.
 */
@Composable
fun DayProgressScreen(
    viewModel: DayProgressViewModel = viewModel()
) {
    // Collect UI state from ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    // Update current time every 60 seconds
    LaunchedEffect(Unit) {
        while (true) {
            viewModel.updateCurrentTime()
            delay(60000) // Update every minute
        }
    }
    
    // Main layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = "Day Progress",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Circular progress ring with percentage
        CircularProgressRing(
            progress = uiState.progress,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        
        // Time remaining card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Time Until Sleep",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${uiState.hoursRemaining}",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "h",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Text(
                        text = "${uiState.minutesRemaining}",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "m",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Time picker fields for wake and sleep times
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Wake time picker
            TimePickerField(
                label = "Wake Time",
                hour = uiState.wakeTimeHour,
                minute = uiState.wakeTimeMinute,
                onTimeSelected = { hour, minute ->
                    viewModel.setWakeTime(hour, minute)
                }
            )
            
            // Sleep time picker
            TimePickerField(
                label = "Sleep Time",
                hour = uiState.sleepTimeHour,
                minute = uiState.sleepTimeMinute,
                onTimeSelected = { hour, minute ->
                    viewModel.setSleepTime(hour, minute)
                }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

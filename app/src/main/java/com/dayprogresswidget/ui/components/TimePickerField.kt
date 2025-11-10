package com.dayprogresswidget.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * TimePickerField provides a text field that opens a time picker dialog when clicked.
 * 
 * This composable combines:
 * - An outlined text field displaying the selected time
 * - A dialog with Material3 TimeInput for selecting time
 * - Automatic formatting of time display (HH:MM)
 * 
 * @param label Label for the text field
 * @param hour Currently selected hour (0-23)
 * @param minute Currently selected minute (0-59)
 * @param onTimeSelected Callback invoked when user selects a time
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField(
    label: String,
    hour: Int,
    minute: Int,
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // State to control dialog visibility
    var showDialog by remember { mutableStateOf(false) }
    
    // Format the time for display (HH:MM)
    val timeText = String.format("%02d:%02d", hour, minute)
    
    // Text field that opens the time picker dialog when clicked
    OutlinedTextField(
        value = timeText,
        onValueChange = { /* Read-only field */ },
        label = { Text(label) },
        readOnly = true,
        modifier = modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
    )
    
    // Show time picker dialog when showDialog is true
    if (showDialog) {
        TimePickerDialog(
            initialHour = hour,
            initialMinute = minute,
            onDismiss = { showDialog = false },
            onConfirm = { selectedHour, selectedMinute ->
                onTimeSelected(selectedHour, selectedMinute)
                showDialog = false
            }
        )
    }
}

/**
 * TimePickerDialog displays a Material3 time picker in a dialog.
 * 
 * @param initialHour Initial hour to display (0-23)
 * @param initialMinute Initial minute to display (0-59)
 * @param onDismiss Callback when dialog is dismissed
 * @param onConfirm Callback when user confirms time selection
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onDismiss: () -> Unit,
    onConfirm: (hour: Int, minute: Int) -> Unit
) {
    // Create time picker state with initial values
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )
    
    Dialog(onDismissRequest = onDismiss) {
        androidx.compose.material3.Surface(
            shape = androidx.compose.material3.MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp
        ) {
            androidx.compose.foundation.layout.Column(
                modifier = Modifier.padding(24.dp)
            ) {
                // Title
                Text(
                    text = "Select Time",
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Time input component
                TimeInput(state = timePickerState)
                
                // Action buttons
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End
                ) {
                    // Cancel button
                    androidx.compose.material3.TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    
                    androidx.compose.foundation.layout.Spacer(
                        modifier = Modifier.width(8.dp)
                    )
                    
                    // Confirm button
                    androidx.compose.material3.TextButton(
                        onClick = {
                            onConfirm(timePickerState.hour, timePickerState.minute)
                        }
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

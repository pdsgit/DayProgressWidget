package com.dayprogresswidget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dayprogresswidget.ui.screens.DayProgressScreen
import com.dayprogresswidget.ui.theme.DayProgressWidgetTheme

/**
 * Main Activity for the Day Progress Widget app.
 * This activity sets up the Compose UI and displays the main screen.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set the Compose content for this activity
        setContent {
            // Apply the custom Material3 theme
            DayProgressWidgetTheme {
                // Create a surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Display the main day progress screen
                    DayProgressScreen()
                }
            }
        }
    }
}

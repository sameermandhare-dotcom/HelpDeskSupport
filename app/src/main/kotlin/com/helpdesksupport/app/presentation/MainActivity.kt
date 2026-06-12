package com.helpdesksupport.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.helpdesksupport.app.presentation.ui.theme.HelpDeskSupportTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity entry point for the app
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelpDeskSupportTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Navigation will be set up here
                    MainScreen()
                }
            }
        }
    }
}

/**
 * Main Screen Composable
 */
@androidx.compose.runtime.Composable
fun MainScreen() {
    // Dashboard content will be rendered here
}

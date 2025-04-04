package com.elvr.quizybara.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Composable
fun LosePopup(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("❌ Game Over!", style = MaterialTheme.typography.headlineMedium, color = Color.Red)
        },
        text = {
            Text("Time ran out! Try again next time.", style = MaterialTheme.typography.bodyLarge)
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        },
        containerColor = Color(0xFFFFD6D6) // Light red background
    )
}

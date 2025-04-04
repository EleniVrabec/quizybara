package com.elvr.quizybara.components

import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun CountdownTimer(
    totalTime: Int, // Total seconds (e.g., 30)
    onTimeUp: () -> Unit, // Callback when time reaches 0
    onTick: (Int) -> Unit // Callback for each second
) {
    var timeLeft by remember { mutableStateOf(totalTime) }

    LaunchedEffect(timeLeft) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
            onTick(timeLeft)
        }
        onTimeUp() // Trigger when time reaches 0
    }
}

package com.elvr.quizybara.components


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.elvr.quizybara.utils.Presets
import kotlinx.coroutines.delay
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import com.elvr.quizybara.ui.theme.ButtonDefaultColor


@Composable
fun BonusPopup(
    message: String,
    showConfetti: Boolean = false,
    confettiType: String = "", // "fireworks" or "balloons"
    backgroundColor: Color = ButtonDefaultColor,
   onDismiss: () -> Unit
) {
    // Bounce animation
    var startAnimation by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 600, easing = {
            // Custom bounce effect
            val overshoot = 1.2f
            val damping = 0.3f
            val t = it.toDouble()
            (overshoot - (overshoot - 1f) * (Math.exp(-t * 5) * Math.cos(t * 10))).toFloat()

        }),
        label = "popupScale"
    )

    // Start animation when the Composable enters
    LaunchedEffect(Unit) {
        startAnimation = true
        delay(3000)
        onDismiss()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.scale(scale)) {
        Surface(
            tonalElevation = 6.dp,
            shadowElevation = 6.dp,
            shape = MaterialTheme.shapes.medium,
            color = backgroundColor,
            modifier = Modifier
                .padding(24.dp)
        ) {
            Box(modifier = Modifier.padding(24.dp)) {
                Text(message,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    textAlign = TextAlign.Center,)


            }
        }
}
        // 🎉 Fireworks or 🎈 Balloons
        if (showConfetti) {
            when (confettiType) {
                "fireworks" -> ConfettiOverlay(
                    show = true,
                    parties = Presets.fireworks(),
                    onComplete = {}
                )
                "balloons" -> BalloonConfetti(
                    show = true,
                    parties = Presets.balloons(),
                    onComplete = {}
                )
            }
        }//if
    }

    // Auto dismiss after 3s
    LaunchedEffect(Unit) {
        delay(3000)
        onDismiss()
    }
}
